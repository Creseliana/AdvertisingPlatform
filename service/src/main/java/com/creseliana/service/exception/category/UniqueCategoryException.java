package com.creseliana.service.exception.category;

import java.io.Serial;

public class UniqueCategoryException extends CategoryServiceException {
    @Serial
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
