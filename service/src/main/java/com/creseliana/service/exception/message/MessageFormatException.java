package com.creseliana.service.exception.message;

import java.io.Serial;

public class MessageFormatException extends MessageServiceException {
    @Serial
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
