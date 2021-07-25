package com.creseliana.repository.exception;

import java.io.Serial;

public class MultiplePaymentMatchingException extends MultipleMatchingException {
    @Serial
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
