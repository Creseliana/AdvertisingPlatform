package com.creseliana.repository.exception;

public class MultipleUserMatchingException extends MultipleMatchingException {
    private static final long serialVersionUID = 6575293600866918982L;

    public MultipleUserMatchingException() {
    }

    public MultipleUserMatchingException(String message) {
        super(message);
    }

    public MultipleUserMatchingException(String message, Throwable cause) {
        super(message, cause);
    }
}
