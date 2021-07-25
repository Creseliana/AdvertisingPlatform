package com.creseliana.service.exception.chat;

import com.creseliana.service.exception.ServiceException;

import java.io.Serial;

public class ChatServiceException extends ServiceException {
    @Serial
    private static final long serialVersionUID = -6822527775810344535L;

    public ChatServiceException() {
    }

    public ChatServiceException(String message) {
        super(message);
    }

    public ChatServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
