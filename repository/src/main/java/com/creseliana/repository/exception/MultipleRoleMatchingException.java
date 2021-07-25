package com.creseliana.repository.exception;

import java.io.Serial;

public class MultipleRoleMatchingException extends MultipleMatchingException {
    @Serial
    private static final long serialVersionUID = -7802441045240995472L;

    public MultipleRoleMatchingException() {
    }

    public MultipleRoleMatchingException(String message) {
        super(message);
    }

    public MultipleRoleMatchingException(String message, Throwable cause) {
        super(message, cause);
    }
}
