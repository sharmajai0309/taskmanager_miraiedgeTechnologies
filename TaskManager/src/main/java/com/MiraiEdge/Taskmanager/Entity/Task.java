package com.MiraiEdge.Taskmanager.Entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

	@Id
    private String id;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private Status status;

    @NotNull
    private Priority priority;

    private String userId; 
	
}



