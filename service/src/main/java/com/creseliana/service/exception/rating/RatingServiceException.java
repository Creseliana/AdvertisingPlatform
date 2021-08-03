package com.creseliana.service.exception.rating;

import com.creseliana.service.exception.ServiceException;

public class RatingServiceException extends ServiceException {
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
