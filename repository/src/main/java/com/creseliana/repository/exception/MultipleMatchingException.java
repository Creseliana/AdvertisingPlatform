package com.creseliana.repository.exception;

import java.io.Serial;

public class MultipleMatchingException extends RepositoryException {
    @Serial
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
