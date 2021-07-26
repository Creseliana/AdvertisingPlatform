package com.creseliana.service.exception;

import java.io.Serial;

public class AccessException extends ServiceException {
    @Serial
    private static final long serialVersionUID = 7272781721864429307L;

    public AccessException() {
    }

    public AccessException(String message) {
        super(message);
    }

    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
