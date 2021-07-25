package com.creseliana.service.exception.rating;

import java.io.Serial;

public class RatingExistsException extends RatingServiceException {
    @Serial
    private static final long serialVersionUID = 2188874295366065438L;

    public RatingExistsException() {
    }

    public RatingExistsException(String message) {
        super(message);
    }

    public RatingExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
