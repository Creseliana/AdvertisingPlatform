package com.creseliana.repository;

import com.creseliana.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class BaseUserRepository extends BaseModelRepository<User> implements UserRepository {
    @Override
    protected Class<User> getModelClass() {
        return User.class;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(getModelClass());
        Root<User> root = query.from(getModelClass());
        query.select(root);
        query.where(builder.equal(root.get("username"), username));
        List<User> users = entityManager.createQuery(query).getResultList();
        if (users.size() > 1) {
            throw new RuntimeException();
            //todo throw exception
        }
        return users.stream().findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(getModelClass());
        query.select(builder.count(root));
        query.where(builder.equal(root.get("email"), email));
        return entityManager.createQuery(query).getSingleResult() != 0;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(getModelClass());
        query.select(builder.count(root));
        query.where(builder.equal(root.get("phone_number"), phoneNumber));
        return entityManager.createQuery(query).getSingleResult() != 0;
    }
}
