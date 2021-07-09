package com.creseliana.repository;

import com.creseliana.model.Model;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Optional;

@Transactional(propagation = Propagation.MANDATORY)
public abstract class BaseModelRepository<T extends Model> implements ModelRepository<Model, Long> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    @Override
    public <S extends Model> S save(S entity) {
        entityManager.persist(entity);
        return null;
    }

    @Override
    public <S extends Model> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Model> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Model> findAll() {
        return null;
    }

    @Override
    public Iterable<Model> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Model entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Model> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
