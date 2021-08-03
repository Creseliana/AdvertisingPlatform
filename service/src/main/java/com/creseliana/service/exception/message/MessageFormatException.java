package com.creseliana.service.exception.message;

public class MessageFormatException extends MessageServiceException {
    private static final long serialVersionUID = 2859278360528071611L;

    public MessageFormatException() {
    }

    public MessageFormatException(String message) {
        super(message);
    }

    public MessageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
