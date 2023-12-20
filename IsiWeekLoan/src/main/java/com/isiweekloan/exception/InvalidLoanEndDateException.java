package com.isiweekloan.exception;

import java.sql.Date;

public class InvalidLoanEndDateException extends RuntimeException {

    public InvalidLoanEndDateException(Date loanEndDate) {
        super(String.format("La fecha de finalización del préstamo %s no es válida.", loanEndDate));
    }

}
