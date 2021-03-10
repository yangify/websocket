package com.gov.dsta.coldstraw.exception.group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException(UUID groupId) {
        super("Could not find group with id: " + groupId);
    }

    public GroupNotFoundException(String groupName) {
        super("Could not find group with name: " + groupName);
    }

}
