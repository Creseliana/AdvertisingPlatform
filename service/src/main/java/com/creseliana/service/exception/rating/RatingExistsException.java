package com.creseliana.service.exception.rating;

public class RatingExistsException extends RatingServiceException {
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
