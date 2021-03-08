package com.gov.dsta.coldstraw.exception.notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(UUID moduleId) {
        super("Could not find notification with id: " + moduleId.toString());
    }
}
