package com.MiraiEdge.Taskmanager.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MiraiEdge.Taskmanager.Entity.Priority;
import com.MiraiEdge.Taskmanager.Entity.Status;
import com.MiraiEdge.Taskmanager.Entity.Task;
import com.MiraiEdge.Taskmanager.Services.TaskServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

	
    private final TaskServices taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, Principal principal) {
        return ResponseEntity.ok(taskService.createTask(task, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(
        @RequestParam(required = false) Status status,
        @RequestParam(required = false) Priority priority,
        Principal principal) {
        return ResponseEntity.ok(taskService.getTasks(principal.getName(), status, priority));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(Principal principal) {
        return ResponseEntity.ok(taskService.getSummary(principal.getName()));
    }
}

