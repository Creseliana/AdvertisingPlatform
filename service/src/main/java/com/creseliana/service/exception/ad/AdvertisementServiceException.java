package com.creseliana.service.exception.ad;

import com.creseliana.service.exception.ServiceException;

import java.io.Serial;

public class AdvertisementServiceException extends ServiceException {
    @Serial
    private static final long serialVersionUID = -3098529337229794001L;

    public AdvertisementServiceException() {
    }

    public AdvertisementServiceException(String message) {
        super(message);
    }

    public AdvertisementServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
