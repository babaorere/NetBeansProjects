package com.isiweek.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("User with username already exists.");
    }

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

}
