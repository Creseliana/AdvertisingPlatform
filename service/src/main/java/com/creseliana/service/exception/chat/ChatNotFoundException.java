package com.creseliana.service.exception.chat;

import com.creseliana.service.exception.EntityNotFoundException;

public class ChatNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = -2627510437810361726L;

    public ChatNotFoundException() {
    }

    public ChatNotFoundException(String message) {
        super(message);
    }

    public ChatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
