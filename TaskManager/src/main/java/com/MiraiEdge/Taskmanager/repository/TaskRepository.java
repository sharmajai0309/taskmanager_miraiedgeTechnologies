package com.MiraiEdge.Taskmanager.repository;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.MiraiEdge.Taskmanager.Entity.Task;

public interface TaskRepository extends MongoRepository<Task, String>{
	
	 List<Task> findByUserId(String userId);
	    List<Task> findByUserIdAndStatus(String userId, String status);
	    List<Task> findByUserIdAndPriority(String userId, String priority);
	    long countByUserIdAndStatus(String userId, String status);
	    long countByUserIdAndDueDateBefore(String userId, java.time.LocalDate date);
		List<Task> find(Query query);
	    
}
