package com.creseliana.repository;

import com.creseliana.model.Model;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Transactional(propagation = Propagation.MANDATORY)
public abstract class BaseModelRepository<T extends Model> implements ModelRepository<T, Long> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    @Override
    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

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
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entityManager.find(getModelClass(), id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Iterable<T> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getModelClass());
        query.from(getModelClass());
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
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
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public long count() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(getModelClass())));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> delete = builder.createCriteriaDelete(getModelClass());
        Root<T> root = delete.from(getModelClass());
        delete.where(builder.equal(root.get("id"), id));
        entityManager.createQuery(delete).executeUpdate();
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

    protected void logMultipleEntitiesOccurrence(List<T> entities) {
        entities.forEach(entity -> log.warn(entity.toString()));
    }

    protected abstract Class<T> getModelClass();
}
