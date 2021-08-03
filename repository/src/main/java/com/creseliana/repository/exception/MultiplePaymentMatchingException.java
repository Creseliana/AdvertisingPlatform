package com.creseliana.repository.exception;

public class MultiplePaymentMatchingException extends MultipleMatchingException {
    private static final long serialVersionUID = 575415590725522336L;

    public MultiplePaymentMatchingException() {
    }

    public MultiplePaymentMatchingException(String message) {
        super(message);
    }

    public MultiplePaymentMatchingException(String message, Throwable cause) {
        super(message, cause);
    }
}
