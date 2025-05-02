package com.MiraiEdge.Taskmanager.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.MiraiEdge.Taskmanager.model.User;

public interface UserRepository extends MongoRepository<User,String> {
	User findByUsername(String username);
    boolean existsByUsername(String username);
}
