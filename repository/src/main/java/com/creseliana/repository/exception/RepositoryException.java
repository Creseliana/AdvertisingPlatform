package com.creseliana.repository.exception;

import java.io.Serial;

public class RepositoryException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7285121201431423580L;

    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
