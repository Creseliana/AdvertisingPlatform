package com.creseliana.service.exception.message;

import com.creseliana.service.exception.ServiceException;

public class MessageServiceException extends ServiceException {
    private static final long serialVersionUID = 5507956335799228993L;

    public MessageServiceException() {
    }

    public MessageServiceException(String message) {
        super(message);
    }

    public MessageServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
