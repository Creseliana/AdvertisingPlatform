package com.creseliana.service.exception.comment;

public class CommentFormatException extends CommentServiceException {
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
