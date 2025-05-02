package com.MiraiEdge.Taskmanager.Entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private String id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	private Set<Role> roles = new HashSet<>();
	
	
}
