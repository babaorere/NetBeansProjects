package com.isiweekloan.exception;

public class InvalidCustomerException extends Exception {

    public InvalidCustomerException(Long customerId) {
        super(String.format("El identificador del cliente %d no es válido.", customerId));
    }

}
