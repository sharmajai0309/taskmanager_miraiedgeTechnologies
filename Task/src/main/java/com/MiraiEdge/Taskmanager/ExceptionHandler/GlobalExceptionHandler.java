package com.MiraiEdge.Taskmanager.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Global exception handler for Task-related exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	
	 /**
     * Handles TaskNotFoundException and other general exceptions
     * @param ex The caught exception
     * @return ResponseEntity containing ErrorResponse and HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(Exception ex) {
        ErrorResponse response = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}