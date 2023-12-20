package com.isiweekloan.exception;

/**
 *
 * @author
 */
public class EntityInUseException extends Exception {

    // Default constructor
    public EntityInUseException(String inCountry_cannot_be_deleted_because_it_has_) {
        super(inCountry_cannot_be_deleted_because_it_has_);
    }
}
