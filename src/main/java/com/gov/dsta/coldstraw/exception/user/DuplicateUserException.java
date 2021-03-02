package com.gov.dsta.coldstraw.exception.user;

import com.gov.dsta.coldstraw.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(User user) {
        super("Duplicate role: " + user.getName().toUpperCase());
    }
}
