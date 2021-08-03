package com.creseliana.service.exception;

public class EntityNotFoundException extends ServiceException {
    private static final long serialVersionUID = -1216837028889664856L;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
