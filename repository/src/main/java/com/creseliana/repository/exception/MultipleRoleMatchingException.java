package com.creseliana.repository.exception;

public class MultipleRoleMatchingException extends MultipleMatchingException {
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
