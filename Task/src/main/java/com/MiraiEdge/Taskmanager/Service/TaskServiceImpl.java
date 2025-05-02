package com.MiraiEdge.Taskmanager.Service;

import java.time.LocalDate; 
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.MiraiEdge.Taskmanager.Repository.TaskRepository;
import com.MiraiEdge.Taskmanager.model.Task;
import com.MiraiEdge.Taskmanager.model.Task.Priority;
import com.MiraiEdge.Taskmanager.model.Task.Status;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
    private TaskRepository taskRepository;
	
	@Autowired
    private  EmailNotificationService emailService;


    /**
     * Creates a new task in the database
     * @param task The task to be created
     * @return The saved task with generated ID
     */
    @Override
    @CacheEvict(value = {"tasks", "taskSummary"}, allEntries = true)
	public Task createTask(Task task) {
    	Task savedTask = taskRepository.save(task);
        emailService.queueEmail(
            "assignee@example.com",
            "New Task Assigned",
            "You have been assigned a new task: " + task.getTitle()
        );
        return savedTask;
    }
    
//  ------------------------------------------------------------------------------------------ 

    /**
     * Updates an existing task
     * @param id ID of the task to update
     * @param task Updated task data
     * @return The updated task
     * Note: Overwrites existing task completely
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "tasks", key = "#id"),
            @CacheEvict(value = "taskSummary", allEntries = true)
        })
    public Task updateTask(String id, Task task) {
    	task.setId(id);
        Task updatedTask = taskRepository.save(task);
        if (task.getStatus() == Status.DONE) {
            emailService.queueEmail(
                "manager@example.com",
                "Task Completed",
                "Task completed: " + task.getTitle()
            );
        }
        return updatedTask;
    }
    
//  ------------------------------------------------------------------------------------------

    /**
     * Deletes a task by ID
     * @param id ID of the task to delete
     * @throws ResponseStatusException if task not found
     */
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "tasks", key = "#id"),
            @CacheEvict(value = "taskSummary", allEntries = true)
        })
    public void deleteTask(String id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Task with ID " + id + " not found"
            );
        }
        taskRepository.deleteById(id);
    }

//  ------------------------------------------------------------------------------------------
    /**
     * Retrieves tasks with optional filtering
     * @param status Optional status filter
     * @param priority Optional priority filter
     * @return List of matching tasks
     */
    @Override
    @Cacheable(value = "tasks", key = "{#status, #priority}")
    public List<Task> getAllTasks(Status status, Priority priority) {
        if (status != null && priority != null) {
            return taskRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            return taskRepository.findByStatus(status);
        } else if (priority != null) {
            return taskRepository.findByPriority(priority);
        }
        return taskRepository.findAll();
    }

//  ------------------------------------------------------------------------------------------   
    /**
     * Generates task analytics summary
     * @return Map containing:
     *   - countByStatus: Task counts grouped by status
     *   - overdueCount: Count of tasks with due dates before today
     */
    @Override
    @Cacheable(value = "taskSummary")
    public Map<String, Object> getTaskSummary() {
        List<Task> allTasks = taskRepository.findAll();
        LocalDate today = LocalDate.now();

        return Map.of(
            "countByStatus", allTasks.stream()
                .collect(Collectors.groupingBy(
                    Task::getStatus, 
                    Collectors.counting()
                )),
            "overdueCount", allTasks.stream()
                .filter(task -> task.getDueDate().isBefore(today))
                .count()
        );
    }
  
//    ------------------------------------------------------------------------------------------
    
    /**
     * Finds a task by ID
     * @param id ID of the task to find
     * @return The found task
     * @throws ResponseStatusException if task not found (HTTP 404)
     */
    @Override
    @Cacheable(value = "tasks", key = "#id")
    public Task findByid(String id) {
        log.debug("Fetching task with ID: {}", id);
        return taskRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Task with ID " + id + " not found"
            ));
    }
}