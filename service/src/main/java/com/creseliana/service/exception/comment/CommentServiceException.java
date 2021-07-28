package com.creseliana.service.exception.comment;

import com.creseliana.service.exception.ServiceException;

import java.io.Serial;

public class CommentServiceException extends ServiceException {
    @Serial
    private static final long serialVersionUID = 8912636086867480452L;

    public CommentServiceException() {
    }

    public CommentServiceException(String message) {
        super(message);
    }

    public CommentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
