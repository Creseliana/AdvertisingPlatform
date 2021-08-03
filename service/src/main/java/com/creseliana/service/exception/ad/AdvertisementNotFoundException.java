package com.creseliana.service.exception.ad;

import com.creseliana.service.exception.EntityNotFoundException;

public class AdvertisementNotFoundException extends EntityNotFoundException {
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
