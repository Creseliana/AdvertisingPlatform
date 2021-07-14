package com.creseliana.repository;

import com.creseliana.model.Model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ModelRepository<T extends Model, ID> extends CrudRepository<T, ID>, PagingAndSortingRepository<T, ID> {

    T update(T entity);
}
