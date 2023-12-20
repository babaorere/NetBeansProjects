package com.isiweekloan.exception;

public class InvalidLoanIdException extends RuntimeException {

    public InvalidLoanIdException(Long loanId) {
        super(String.format("El identificador de préstamo %d no es válido.", loanId));
    }

}
