package org.example.dinger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNameAlreadyExistedException extends ResponseStatusException {
    public UserNameAlreadyExistedException() {
        super(HttpStatus.BAD_REQUEST, "User name already existed");
    }
}
