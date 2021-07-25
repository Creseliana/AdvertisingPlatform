package com.creseliana.service;

import com.creseliana.dto.CommentShowResponse;

import java.util.List;

public interface CommentService {

    void create(String username, Long id, String comment);

    List<CommentShowResponse> getAll(Long id, int page, int amount);
}
