package com.isiweekloan.exception;

public class BadRequestException extends Exception {

    // Default constructor
    public BadRequestException(String inCountry_cannot_be_deleted_because_it_has_) {
        super(inCountry_cannot_be_deleted_because_it_has_);
    }
}