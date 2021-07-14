package com.creseliana.service;

import com.creseliana.dto.CommentCreateRequest;

public interface CommentService {

    void create(String username, Long id, CommentCreateRequest newComment);
}
