package com.creseliana.service.exception.category;

public class UniqueCategoryException extends CategoryServiceException {
    private static final long serialVersionUID = 7082641242122221917L;

    public UniqueCategoryException() {
    }

    public UniqueCategoryException(String message) {
        super(message);
    }

    public UniqueCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
