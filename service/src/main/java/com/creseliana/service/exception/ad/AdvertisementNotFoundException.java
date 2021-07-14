package com.creseliana.service.exception.ad;

import com.creseliana.service.exception.EntityNotFoundException;

import java.io.Serial;

public class AdvertisementNotFoundException extends EntityNotFoundException {
    @Serial
    private static final long serialVersionUID = -6407852052403377028L;

    public AdvertisementNotFoundException() {
    }

    public AdvertisementNotFoundException(String message) {
        super(message);
    }

    public AdvertisementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
