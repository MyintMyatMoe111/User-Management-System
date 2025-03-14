package org.example.dinger.exception;

public class UserNotFoundException extends  RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
