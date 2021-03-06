package com.creseliana.service.exception.rating;

public class UserRateException extends RatingServiceException {
    private static final long serialVersionUID = -6339676155202904090L;

    public UserRateException() {
    }

    public UserRateException(String message) {
        super(message);
    }

    public UserRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
