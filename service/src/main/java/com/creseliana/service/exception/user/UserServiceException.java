package com.creseliana.service.exception.user;

import com.creseliana.service.exception.ServiceException;

public class UserServiceException extends ServiceException {
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
