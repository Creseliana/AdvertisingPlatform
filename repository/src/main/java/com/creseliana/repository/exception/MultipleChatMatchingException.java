package com.creseliana.repository.exception;

import java.io.Serial;

public class MultipleChatMatchingException extends MultipleMatchingException {
    @Serial
    private static final long serialVersionUID = 4192420243562184485L;

    public MultipleChatMatchingException() {
    }

    public MultipleChatMatchingException(String message) {
        super(message);
    }

    public MultipleChatMatchingException(String message, Throwable cause) {
        super(message, cause);
    }
}
