package com.creseliana.service.exception;

import java.io.Serial;

public class UniqueValueException extends UserServiceException {
    @Serial
    private static final long serialVersionUID = -532241837656333256L;

    public UniqueValueException() {
    }

    public UniqueValueException(String message) {
        super(message);
    }

    public UniqueValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
