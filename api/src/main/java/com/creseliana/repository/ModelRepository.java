package com.creseliana.repository;

import com.creseliana.model.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepository<T extends Model, ID> extends CrudRepository<T, ID> {
}
