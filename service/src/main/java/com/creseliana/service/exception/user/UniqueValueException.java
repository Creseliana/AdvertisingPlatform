package com.creseliana.service.exception.user;

public class UniqueValueException extends UserServiceException {
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
