package com.creseliana.repository;

import com.creseliana.model.Comment;

import java.util.List;

public interface CommentRepository extends ModelRepository<Comment, Long> {

    List<Comment> getCommentsByAdId(Long id, int start, int amount);
}
