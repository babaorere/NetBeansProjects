package com.isiweekloan.exception;

public class InvalidLoanInterestRateException extends RuntimeException {

    public InvalidLoanInterestRateException(Double loanInterestRate) {
        super(String.format("La tasa de interés del préstamo %.2f no es válida.", loanInterestRate));
    }

}
