package com.creseliana.service.exception.chat;

import com.creseliana.service.exception.EntityNotFoundException;

import java.io.Serial;

public class ChatNotFoundException extends EntityNotFoundException {
    @Serial
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
