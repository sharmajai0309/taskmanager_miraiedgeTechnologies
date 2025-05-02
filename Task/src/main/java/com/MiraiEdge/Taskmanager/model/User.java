package com.MiraiEdge.Taskmanager.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User implements Serializable{
    private static final long serialVersionUID = 6490256397316136672L;
	@Id
    private String id;
    private String username;
    private String password;
}
