package com.MiraiEdge.Taskmanager.Services;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.MiraiEdge.Taskmanager.Entity.Priority;
import com.MiraiEdge.Taskmanager.Entity.Status;
import com.MiraiEdge.Taskmanager.Entity.Task;
import com.MiraiEdge.Taskmanager.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServices {
	
	@Autowired
	private final TaskRepository taskRepo;
	
	@Autowired
	private MongoTemplate mongoTemplate;

    public Task createTask(Task task, String username) {
        task.setUserId(username);
        return taskRepo.save(task);
    }

    public List<Task> getTasks(String userId, Status status, Priority priority) {
    	Criteria criteria = Criteria.where("userId").is(userId);

        if (status != null) {
            criteria = criteria.and("status").is(status);
        }

        if (priority != null) {
            criteria = criteria.and("priority").is(priority);
        }

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Task.class);

    }

    public Task updateTask(String id, Task task) {
        Task existing = taskRepo.findById(id).orElseThrow();
        task.setId(existing.getId());
        task.setUserId(existing.getUserId());
        return taskRepo.save(task);
    }

    public void deleteTask(String id) {
        taskRepo.deleteById(id);
    }

    public Map<String, Object> getSummary(String username) {
        List<Task> tasks = taskRepo.findByUserId(username);
        Map<Status, Long> countByStatus = tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        long overdueCount = tasks.stream()
                .filter(t -> t.getDueDate().isBefore(LocalDate.now()) && t.getStatus() != Status.DONE)
                .count();

        Map<String, Object> result = new HashMap<>();
        result.put("statusCount", countByStatus);
        result.put("overdueTasks", overdueCount);
        return result;
    }
}
