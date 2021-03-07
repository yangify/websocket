package com.gov.dsta.coldstraw.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID userId) {
        super("Could not find user with id: " + userId);
    }

    public UserNotFoundException(String name) {
        super("Could not find user with name: " + name);
    }
}