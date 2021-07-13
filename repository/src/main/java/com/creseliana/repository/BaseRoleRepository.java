package com.creseliana.repository;

import com.creseliana.RoleType;
import com.creseliana.model.Role;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class BaseRoleRepository extends BaseModelRepository<Role> implements RoleRepository {
    @Override
    protected Class<Role> getModelClass() {
        return Role.class;
    }

    @Override
    public Optional<Role> getRoleByType(RoleType type) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = builder.createQuery(getModelClass());
        Root<Role> root = query.from(getModelClass());
        query.select(root);
        query.where(builder.equal(root.get("type"), type));
        List<Role> roles = entityManager.createQuery(query).getResultList();
        if (roles.size() > 1) {
            throw new RuntimeException();
            //todo throw
        }
        return roles.stream().findFirst();
    }
}
