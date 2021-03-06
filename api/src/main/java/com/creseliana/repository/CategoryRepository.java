package com.creseliana.repository;

import com.creseliana.model.Category;

public interface CategoryRepository extends ModelRepository<Category, Long> {

    boolean existsByName(String name);
}
