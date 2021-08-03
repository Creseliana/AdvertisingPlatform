package com.creseliana.repository.exception;

public class MultipleMatchingException extends RepositoryException {
    private static final long serialVersionUID = 1608470261298800740L;

    public MultipleMatchingException() {
    }

    public MultipleMatchingException(String message) {
        super(message);
    }

    public MultipleMatchingException(String message, Throwable cause) {
        super(message, cause);
    }
}
