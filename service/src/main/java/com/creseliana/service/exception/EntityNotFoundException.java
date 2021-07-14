package com.creseliana.service.exception;

import java.io.Serial;

public class EntityNotFoundException extends ServiceException {
    @Serial
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
