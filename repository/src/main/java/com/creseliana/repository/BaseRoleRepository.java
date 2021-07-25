package com.creseliana.repository;

import com.creseliana.RoleType;
import com.creseliana.model.Role;
import com.creseliana.repository.exception.MultipleRoleMatchingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class BaseRoleRepository extends BaseModelRepository<Role> implements RoleRepository {
    private static final String MSG_MULTIPLE_ROLES = "There are more than one role matching role type '%s'";

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
            String msg = String.format(MSG_MULTIPLE_ROLES, type.toString());
            log.warn(msg);
            throw new MultipleRoleMatchingException(msg); //todo try to solve it?
        }
        return roles.stream().findFirst();
    }
}
