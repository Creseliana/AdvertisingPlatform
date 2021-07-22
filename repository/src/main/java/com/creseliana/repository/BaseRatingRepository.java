package com.creseliana.repository;

import com.creseliana.model.Rating;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class BaseRatingRepository extends BaseModelRepository<Rating> implements RatingRepository {
    @Override
    protected Class<Rating> getModelClass() {
        return Rating.class;
    }

    @Override
    public List<Rating> getRatingsByUserId(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rating> query = builder.createQuery(getModelClass());
        Root<Rating> root = query.from(getModelClass());
        query.select(root);
        Predicate isUserId = builder.equal(root.get("user_id"), id);
        query.where(isUserId);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean existsByUserIdAndRaterId(Long userId, Long raterId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Rating> root = query.from(getModelClass());
        Predicate isUser = builder.equal(root.get("user_id"), userId);
        Predicate isRater = builder.equal(root.get("rater_id"), raterId);
        Predicate isUserAndRater = builder.and(isUser, isRater);
        query.select(builder.count(root));
        query.where(isUserAndRater);
        return entityManager.createQuery(query).getSingleResult() != 0;
    }
}
