package com.gov.dsta.coldstraw.validator.parameter;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class NotificationParameterValidator {

    public void validate(HttpServletRequest request) {
        String requestType = request.getMethod();
        switch (requestType.toUpperCase()) {
            case "GET":     validateGetRequest(request);
            case "POST":    validatePostRequest(request);
            case "PUT":     validatePutRequest(request);
            case "DELETE":  validateDeleteRequest(request);
        }
    }

    private void validateGetRequest(HttpServletRequest request) {
    }

    private void validatePostRequest(HttpServletRequest request) {
    }

    private void validatePutRequest(HttpServletRequest request) {
    }

    private void validateDeleteRequest(HttpServletRequest request) {
    }
}
