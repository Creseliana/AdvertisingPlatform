package com.creseliana.repository;

import com.creseliana.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public class BaseCategoryRepository extends BaseModelRepository<Category> implements CategoryRepository {
    @Override
    protected Class<Category> getModelClass() {
        return Category.class;
    }
}
