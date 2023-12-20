package com.isiweekloan.exception;

public class InvalidLoanAmountException extends RuntimeException {

    public InvalidLoanAmountException(Double loanAmount) {
        super(String.format("La cantidad del préstamo %.2f no es válida.", loanAmount));
    }

}
