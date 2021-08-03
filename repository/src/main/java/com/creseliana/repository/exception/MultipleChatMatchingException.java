package com.creseliana.repository.exception;

public class MultipleChatMatchingException extends MultipleMatchingException {
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
