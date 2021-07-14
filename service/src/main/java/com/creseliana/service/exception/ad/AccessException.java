package com.creseliana.service.exception.ad;

import java.io.Serial;

public class AccessException extends AdvertisementServiceException {
    @Serial
    private static final long serialVersionUID = 7272781721864429307L;

    public AccessException() {
    }

    public AccessException(String message) {
        super(message);
    }

    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
