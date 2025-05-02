package com.MiraiEdge.Taskmanager.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.time.LocalDate;

@Document
@Data
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private String userId;

    
}