package ru.mail.npv90.general.handler;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleEntityExists(RuntimeException e) {
        return Map.of(
                "status", "CONFLICT",
                "reason", "Object already exists",
                "message", e.getMessage()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEntityNotFound(RuntimeException e) {
        return Map.of(
                "status", "NOT FOUND",
                "reason", "Object not found",
                "message", e.getMessage()
        );
    }
}
