package com.creseliana.service.exception.user;

public class PhoneNumberFormatException extends UserServiceException {
    private static final long serialVersionUID = 7536550545230388170L;

    public PhoneNumberFormatException() {
    }

    public PhoneNumberFormatException(String message) {
        super(message);
    }

    public PhoneNumberFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
