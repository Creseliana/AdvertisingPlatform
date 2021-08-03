package com.creseliana.service.exception.user;

public class UsernameFormatException extends UserServiceException {
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
