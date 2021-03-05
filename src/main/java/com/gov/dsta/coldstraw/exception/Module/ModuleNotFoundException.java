package com.gov.dsta.coldstraw.exception.Module;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ModuleNotFoundException extends RuntimeException {
    public ModuleNotFoundException(Long moduleId) {
        super("Could not find module with id: " + moduleId);
    }
}
