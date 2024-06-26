package com.isiweek.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super("Email not found");
    }

    public EmailNotFoundException(final String message) {
        super(message);
    }

}
