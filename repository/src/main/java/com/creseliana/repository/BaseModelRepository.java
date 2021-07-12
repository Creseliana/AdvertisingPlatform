package com.creseliana.repository;

import com.creseliana.model.Model;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(propagation = Propagation.MANDATORY)
public abstract class BaseModelRepository<T extends Model> implements ModelRepository<T, Long> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    @Override
    public <S extends T> S save(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> list = new ArrayList<>();

        for (S entity : entities) {
            list.add(save(entity));
        }

        return list;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entityManager.find(getModelClass(), id));
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<T> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getModelClass());
        query.from(getModelClass());
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Iterable<T> findAllById(Iterable<Long> ids) {
        T entity;
        List<T> entities = new ArrayList<>();

        for (Long id : ids) {
            entity = findById(id).orElse(null);

            if (entity != null) {
                entities.add(entity);
            }
        }
        return entities;
    }

    @Override
    public long count() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(getModelClass())));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        T entity = findById(id).orElseThrow(); //todo handle
        delete(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        T entity;

        for (Long id : ids) {
            entity = findById(id).orElse(null);

            if (entity != null) {
                delete(entity);
            }
        }
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> delete = builder.createCriteriaDelete(getModelClass());
        delete.from(getModelClass());
        entityManager.createQuery(delete).executeUpdate();
    }

    protected abstract Class<T> getModelClass();
}
