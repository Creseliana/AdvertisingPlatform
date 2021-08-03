package com.creseliana.service.exception.ad;

import com.creseliana.service.exception.ServiceException;

public class AdvertisementServiceException extends ServiceException {
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
