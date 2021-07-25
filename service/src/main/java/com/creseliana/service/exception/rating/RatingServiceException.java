package com.creseliana.service.exception.rating;

import com.creseliana.service.exception.ServiceException;

import java.io.Serial;

public class RatingServiceException extends ServiceException {
    @Serial
    private static final long serialVersionUID = -4129715154157258949L;

    public RatingServiceException() {
    }

    public RatingServiceException(String message) {
        super(message);
    }

    public RatingServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
