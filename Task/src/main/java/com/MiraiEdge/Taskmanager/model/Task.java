package com.MiraiEdge.Taskmanager.model;

import java.io.ObjectInputFilter.Status;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Document(collection = "tasks")
public class Task implements Serializable{
	private static final long serialVersionUID = -690125362511595951L;

	@Id
    private String id;
	
	@NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    
    @NotBlank(message = "DueDate is mandatory")
    private LocalDate dueDate;
    
    private Status status;
    private Priority priority;
    
    public enum Status {
        TODO, IN_PROGRESS, DONE
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
}
