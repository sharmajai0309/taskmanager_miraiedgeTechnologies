package com.MiraiEdge.Taskmanager.restController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("v1/api/tasks")
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

    
  //------------------------------------------------------------------------------------------------------------------------------------            
    
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
        
//------------------------------------------------------------------------------------------------------------------------------------              
        
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
//------------------------------------------------------------------------------------------------------------------------------------              
        
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
        
//------------------------------------------------------------------------------------------------------------------------------------               
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
//------------------------------------------------------------------------------------------------------------------------------------               
        /**
         * Get tasks by status with pagination
         * @param status Task status (OPEN, IN_PROGRESS, etc.)
         * @param page Page number (0-based)
         * @param size Page size
         * @param sort Sort property (e.g., "dueDate,asc")
         * @return Paginated tasks
         */
        
        
        @Operation(
                summary = "Get tasks by status",
                description = "Retrieves paginated tasks filtered by status with sorting support",
                parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "status", description = "Task status (OPEN, IN_PROGRESS, etc.)", required = true),
                    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Page number (0-based)", schema = @Schema(defaultValue = "0")),
                    @Parameter(in = ParameterIn.QUERY, name = "size", description = "Page size", schema = @Schema(defaultValue = "10")),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", description = "Sorting criteria (format: property,asc|desc)", schema = @Schema(defaultValue = "dueDate,asc"))
                }
            )
        @GetMapping("/status/{status}")
        public ResponseEntity<Page<Task>> getTasksByStatus(
                @PathVariable Task.Status status,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "dueDate,asc") String sort) {

            PageRequest pageable = PageRequest.of(page, size, Sort.by(sort.split(",")));
            return ResponseEntity.ok(taskService.getTasksByStatus(status, pageable));
        }
        
//------------------------------------------------------------------------------------------------------------------------------------        
        /**
         * Get overdue tasks
         * @param date Cutoff date (ISO format: yyyy-MM-dd)
         * @param page Page number
         * @param size Page size
         * @return Paginated overdue tasks
         */
        @Operation(
                summary = "Get overdue tasks",
                description = "Retrieves tasks with due dates before the specified date",
                parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "date", description = "Cutoff date (ISO format)", required = true),
                    @Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(defaultValue = "0")),
                    @Parameter(in = ParameterIn.QUERY, name = "size", schema = @Schema(defaultValue = "10"))
                }
            )
        @GetMapping("/overdue")
        public ResponseEntity<Page<Task>> getOverdueTasks(
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size) {

            PageRequest pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(taskService.getOverdueTasks(date, pageable));
        }
    }

