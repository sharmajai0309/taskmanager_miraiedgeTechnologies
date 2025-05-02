package com.MiraiEdge.Taskmanager.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.MiraiEdge.Taskmanager.model.Task;

public interface TaskRepository extends MongoRepository<Task,String> {
	 List<Task> findByStatus(Task.Status status);
	    List<Task> findByPriority(Task.Priority priority);
	    List<Task> findByStatusAndPriority(Task.Status status, Task.Priority priority);
	    List<Task> findByDueDateBefore(LocalDate date);

}
