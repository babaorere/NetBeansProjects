package com.isiweek.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StatusNotFoundException extends RuntimeException {

    public StatusNotFoundException() {
        super("Status not found.");
    }

    public StatusNotFoundException(final String message) {
        super(message);
    }

}
