package com.creseliana.service.exception.user;

public class EmailFormatException extends UserServiceException {
    private static final long serialVersionUID = 8693359016611248511L;

    public EmailFormatException() {
    }

    public EmailFormatException(String message) {
        super(message);
    }

    public EmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
