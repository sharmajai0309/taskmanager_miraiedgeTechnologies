package com.MiraiEdge.Taskmanager.Repository;
 
import java.time.LocalDate; 
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.MiraiEdge.Taskmanager.model.Task;
/**
 * MongoDB repository for {@link Task} entities providing custom query methods.
 */
public interface TaskRepository extends MongoRepository<Task,String> {
	
	
	
	
	/**
     * Finds all tasks with the specified status.
     * @param status The task status to filter by
     * @return List of matching tasks, empty list if none found
     */
    List<Task> findByStatus(Task.Status status);

    /**
     * Finds all tasks with the specified priority.
     * @param priority The task priority to filter by
     * @return List of matching tasks, empty list if none found
     */
    List<Task> findByPriority(Task.Priority priority);

    /**
     * Finds tasks matching both status and priority criteria.
     * @param status The task status to filter by
     * @param priority The task priority to filter by
     * @return List of matching tasks, empty list if none found
     */
    List<Task> findByStatusAndPriority(Task.Status status, Task.Priority priority);

    /**
     * Finds tasks with due dates before the specified date.
     * @param date The cutoff date (exclusive)
     * @return List of overdue tasks, empty list if none found
     */
    List<Task> findByDueDateBefore(LocalDate date);
    

    // Paginated version of status query
    Page<Task> findByStatus(Task.Status status, Pageable pageable);
    
    // Paginated version of due date query
    Page<Task> findByDueDateBefore(LocalDate date, Pageable pageable);
}
