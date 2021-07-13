package com.creseliana.service.exception;

import java.io.Serial;

public class UserServiceException extends ServiceException {
    @Serial
    private static final long serialVersionUID = 7346547254775856794L;

    public UserServiceException() {
        super();
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
