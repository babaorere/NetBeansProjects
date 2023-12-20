package com.isiweekloan.exception;

public class InvalidLoanStatusException extends RuntimeException {

    public InvalidLoanStatusException(String loanStatus) {
        super(String.format("El estado del préstamo %s no es válido.", loanStatus));
    }

}
