package com.gov.dsta.coldstraw.exception.module;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ModuleNotFoundException extends RuntimeException {

    public ModuleNotFoundException(UUID moduleId) {
        super("Could not find module with id: " + moduleId);
    }

    public ModuleNotFoundException(String moduleName) {
        super("Could not find module with name: " + moduleName);
    }
}
