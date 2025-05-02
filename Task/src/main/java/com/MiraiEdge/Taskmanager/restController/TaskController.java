package com.MiraiEdge.Taskmanager.restController;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.MiraiEdge.Taskmanager.Repository.TaskRepository;
import com.MiraiEdge.Taskmanager.Service.JwtUtil;
import com.MiraiEdge.Taskmanager.Service.TaskService;
import com.MiraiEdge.Taskmanager.model.Task;
import com.MiraiEdge.Taskmanager.model.Task.Status;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Management", description = "Operations related to task management")
public class TaskController {

    @Autowired
    private TaskService taskService;

    
    /**
     * Creates a new task
     */
    @Operation(
            summary = "Create a new task",
            description = "Creates a new task with the provided details",
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))
                )
            }
        )
        @PostMapping
        public Task createTask(@RequestBody Task task) {
            return taskService.createTask(task);
        }

    
    
    
    /**
     * Retrieves tasks with optional filtering
     */
        @Operation(
            summary = "Get all tasks",
            description = "Retrieves all tasks with optional filtering by status and/or priority",
            parameters = {
                @Parameter(name = "status", description = "Filter by task status", example = "TODO"),
                @Parameter(name = "priority", description = "Filter by task priority", example = "HIGH")
            }
        )
        @GetMapping
        public List<Task> getTasks(
                @RequestParam(required = false) Task.Status status,
                @RequestParam(required = false) Task.Priority priority) {
            return taskService.getAllTasks(status, priority);
        }
        
        
        
        /**
         * Retrieves task analytics
         */
        @Operation(
            summary = "Get task summary",
            description = "Returns analytics about tasks (count by status, overdue tasks)"
        )
        @GetMapping("/summary")
        public Map<String, Object> getTaskSummary() {
            return taskService.getTaskSummary();
        }
        
        
        /**
         * Schema for Task Summary response
         */
        @Schema(name = "TaskSummary", description = "Task analytics summary")
        private static class TaskSummary {
            @Schema(description = "Count of tasks grouped by status")
            public Map<Status, Long> countByStatus;
            
            @Schema(description = "Count of overdue tasks")
            public Long overdueCount;
        }
        
        
        /**
         * Handles validation errors
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                errors.put(fieldName, error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
    }

