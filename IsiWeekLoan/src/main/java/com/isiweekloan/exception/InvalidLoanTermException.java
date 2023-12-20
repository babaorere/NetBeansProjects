package com.isiweekloan.exception;

public class InvalidLoanTermException extends RuntimeException {

    public InvalidLoanTermException(Integer loanTerm) {
        super(String.format("El plazo del préstamo %d no es válido.", loanTerm));
    }

}
