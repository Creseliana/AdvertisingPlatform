package com.creseliana.repository;

import com.creseliana.model.User;
import com.creseliana.repository.exception.MultipleUserMatchingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class BaseUserRepository extends BaseModelRepository<User> implements UserRepository {
    private static final String MSG_MULTIPLE_USERS = "There are more than one user matching username '%s'";

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
            String msg = String.format(MSG_MULTIPLE_USERS, username);
            log.warn(msg);
            throw new MultipleUserMatchingException(msg); //todo try to do something?
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
        query.where(builder.equal(root.get("phoneNumber"), phoneNumber));
        return entityManager.createQuery(query).getSingleResult() != 0;
    }
}
