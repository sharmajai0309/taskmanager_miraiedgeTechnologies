package com.MiraiEdge.Taskmanager.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MiraiEdge.Taskmanager.Model.Task;
import com.MiraiEdge.Taskmanager.jwtconfig.JwtUtil;
import com.MiraiEdge.Taskmanager.repository.TaskRepository;
import com.MiraiEdge.Taskmanager.repository.UserRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	

    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;

    private String extractUserId(String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return userRepository.findByUsername(username).orElseThrow().getId();
    }

    @GetMapping
    public List<Task> getTasks(@RequestHeader("Authorization") String token) {
        return taskRepository.findByUserId(extractUserId(token));
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, @RequestHeader("Authorization") String token) {
        task.setUserId(extractUserId(token));
        System.out.println("logged");
        
        return taskRepository.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskRepository.deleteById(id);
    }

    @GetMapping("/summary")
    public String summary(@RequestHeader("Authorization") String token) {
        String userId = extractUserId(token);
        List<Task> tasks = taskRepository.findByUserId(userId);
        long todo = tasks.stream().filter(t -> "todo".equals(t.getStatus())).count();
        long inProgress = tasks.stream().filter(t -> "in_progress".equals(t.getStatus())).count();
        long done = tasks.stream().filter(t -> "done".equals(t.getStatus())).count();
        long overdue = tasks.stream().filter(t -> t.getDueDate().isBefore(java.time.LocalDate.now())).count();
        return String.format("Todo: %d, In Progress: %d, Done: %d, Overdue: %d", todo, inProgress, done, overdue);
    }
}
