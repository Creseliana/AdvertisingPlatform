package com.creseliana.service.exception.category;

import com.creseliana.service.exception.ServiceException;

public class CategoryServiceException extends ServiceException {
    private static final long serialVersionUID = 6621737670024882710L;

    public CategoryServiceException() {
    }

    public CategoryServiceException(String message) {
        super(message);
    }

    public CategoryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
