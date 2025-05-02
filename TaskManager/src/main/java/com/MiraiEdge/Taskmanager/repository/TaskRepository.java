package com.MiraiEdge.Taskmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.MiraiEdge.Taskmanager.Model.Task;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserId(String userId);
}
