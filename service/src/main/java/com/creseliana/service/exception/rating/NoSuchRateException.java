package com.creseliana.service.exception.rating;

import java.io.Serial;

public class NoSuchRateException extends RatingServiceException {
    @Serial
    private static final long serialVersionUID = -5870024128389817182L;

    public NoSuchRateException() {
    }

    public NoSuchRateException(String message) {
        super(message);
    }

    public NoSuchRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
