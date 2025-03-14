package org.example.dinger.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UserNameAlreadyExistedException.class, EntityNotFoundException.class})
    public ResponseEntity<ApiError>
    handleError(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
