package com.creseliana.service.exception;

import java.io.Serial;

public class PhoneNumberFormatException extends UserServiceException {
    @Serial
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
