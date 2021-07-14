package com.creseliana.service.exception.user;

import com.creseliana.service.exception.EntityNotFoundException;

import java.io.Serial;

public class UserNotFoundException extends EntityNotFoundException {
    @Serial
    private static final long serialVersionUID = -7871318984658705326L;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
