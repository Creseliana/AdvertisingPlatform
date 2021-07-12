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
    public Optional<User> findByUserName(String userName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(getModelClass());
        Root<User> root = query.from(getModelClass());
        query.select(root);
        query.where(builder.equal(root.get("user_name"), userName));
        List<User> users = entityManager.createQuery(query).getResultList();
        if (users.isEmpty()) {
            throw new RuntimeException();
            //todo throw exception
        } else if (users.size() > 1) {
            throw new RuntimeException();
            //todo throw exception
        }
        return users.stream().findFirst();
    }

}
