package com.isiweekloan.exception;

public class InvalidPaymentException extends RuntimeException {

    public InvalidPaymentException(Double paymentAmount) {
        super(String.format("La cantidad del pago %.2f no es válida.", paymentAmount));
    }

}
