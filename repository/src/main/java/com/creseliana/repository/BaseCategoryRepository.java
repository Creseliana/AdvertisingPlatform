package com.creseliana.repository;

import com.creseliana.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class BaseCategoryRepository extends BaseModelRepository<Category> implements CategoryRepository {

    @Override
    protected Class<Category> getModelClass() {
        return Category.class;
    }

    @Override
    public boolean existsByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Category> root = query.from(getModelClass());
        query.select(builder.count(root));
        query.where(builder.equal(root.get("name"), name));
        return entityManager.createQuery(query).getSingleResult() != 0;
    }
}
