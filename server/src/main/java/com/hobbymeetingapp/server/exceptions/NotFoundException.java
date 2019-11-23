package com.hobbymeetingapp.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, "Not found");
    }

    public NotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, "Not found: " + reason);
    }
}