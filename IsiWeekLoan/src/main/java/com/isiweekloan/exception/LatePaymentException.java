package com.isiweekloan.exception;

import java.sql.Date;

public class LatePaymentException extends RuntimeException {

    public LatePaymentException(Date paymentDueDate, Date paymentDate) {
        super(String.format("El pago vence el %s y se realizó el %s.", paymentDueDate, paymentDate));
    }

}
