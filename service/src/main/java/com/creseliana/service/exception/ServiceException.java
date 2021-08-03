package com.creseliana.service.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -1315386344390485511L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
