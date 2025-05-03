package com.MiraiEdge.Taskmanager.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.MiraiEdge.Taskmanager.Repository.TaskRepository;
import com.MiraiEdge.Taskmanager.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;



/**
 * Service interface for task management operations
 */
public interface TaskService {
	
	 /**
     * Creates a new task
     * @param task The task to be created (must not be null)
     * @return The created task with generated ID
     */
	     Task createTask(Task task);
	 
	 
	  
	     /**
	      * Finds a task by its ID
	     * @param id The ID of the task to find (must not be empty)
	     * @return The found task
	     * @throws org.springframework.web.server.ResponseStatusException if task not found (HTTP 404)
	     */
	 
	     Task findByid(String id);
	 

	    /**
	     * Updates an existing task
	     * @param id ID of the task to update (must match task.id)
	     * @param task Updated task data
	     * @return The updated task
	     */
	    Task updateTask(String id, Task task);
	    
	    
	    /**
	     * Deletes a task by ID
	     * @param id ID of the task to delete
	     * @throws org.springframework.web.server.ResponseStatusException if task not found (HTTP 404)
	     */
	    void deleteTask(String id);
	    
	    /**
	     * Retrieves tasks with optional filtering
	     * @param status Optional status filter (nullable)
	     * @param priority Optional priority filter (nullable)
	     * @return List of matching tasks (never null, may be empty)
	     */
	    
	    
	    
	    List<Task> getAllTasks(Task.Status status, Task.Priority priority);
	    
	    
	    /**
	     * Generates task analytics summary
	     * @return Map containing:
	     *         - "countByStatus": Task counts grouped by status
	     *         - "overdueCount": Count of tasks with due dates before today
	     */
	    Map<String, Object> getTaskSummary();
	    
	    
	    
	    /**
	     * Get paginated tasks by status
	     * @param status Task status filter
	     * @param pageable Pagination and sorting parameters
	     * @return Page of tasks
	     */
	    public Page<Task> getTasksByStatus(Task.Status status, Pageable pageable);
	    
	    /**
	     * Get overdue tasks (due before specified date)
	     * @param date Cutoff date
	     * @param pageable Pagination and sorting parameters
	     * @return Page of overdue tasks
	     */
	    public Page<Task> getOverdueTasks(LocalDate date, Pageable pageable);
}












