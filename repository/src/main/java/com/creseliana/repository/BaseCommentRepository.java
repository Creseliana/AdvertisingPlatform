package com.creseliana.repository;

import com.creseliana.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BaseCommentRepository extends BaseModelRepository<Comment> implements CommentRepository {
    @Override
    protected Class<Comment> getModelClass() {
        return Comment.class;
    }

    @Override
    public List<Comment> getCommentsByAdId(Long id, int start, int amount) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = builder.createQuery(getModelClass());
        Root<Comment> root = query.from(getModelClass());
        query.select(root);
        query.where(builder.equal(root.get("ad").get("id"), id));
        query.orderBy(builder.desc(root.get("date")));
        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList();
    }
}
