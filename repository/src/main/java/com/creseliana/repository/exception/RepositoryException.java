package com.creseliana.repository.exception;

public class RepositoryException extends RuntimeException {
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
