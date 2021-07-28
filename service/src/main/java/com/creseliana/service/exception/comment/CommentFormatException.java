package com.creseliana.service.exception.comment;

import java.io.Serial;

public class CommentFormatException extends CommentServiceException {
    @Serial
    private static final long serialVersionUID = 6333108754803347238L;

    public CommentFormatException() {
    }

    public CommentFormatException(String message) {
        super(message);
    }

    public CommentFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
