package org.example.dinger.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


public class ApiError {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ApiError(int statusCode, String message, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ApiError() {
    }
}
