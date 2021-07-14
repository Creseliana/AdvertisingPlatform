package com.creseliana.service.exception.user;

import java.io.Serial;

public class UsernameFormatException extends UserServiceException {
    @Serial
    private static final long serialVersionUID = -3903649659919624904L;

    public UsernameFormatException() {
    }

    public UsernameFormatException(String message) {
        super(message);
    }

    public UsernameFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
