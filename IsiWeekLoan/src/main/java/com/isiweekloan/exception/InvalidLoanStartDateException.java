package com.isiweekloan.exception;

import java.sql.Date;

public class InvalidLoanStartDateException extends RuntimeException {

    public InvalidLoanStartDateException(Date loanStartDate) {
        super(String.format("La fecha de inicio del préstamo %s no es válida.", loanStartDate));
    }

}
