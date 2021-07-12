package com.creseliana.repository;

import com.creseliana.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class BaseCommentRepository extends BaseModelRepository<Comment> implements CommentRepository {
    @Override
    protected Class<Comment> getModelClass() {
        return Comment.class;
    }
}
