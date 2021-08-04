package com.creseliana.service.exception.category;

import com.creseliana.service.exception.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = -3613515670050250686L;

    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
