package com.creseliana.repository;

import com.creseliana.RoleType;
import com.creseliana.model.Role;
import com.creseliana.repository.exception.MultipleRoleMatchingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Repository
public class BaseRoleRepository extends BaseModelRepository<Role> implements RoleRepository {
    private static final String MSG_MULTIPLE_ROLES = "There are more than one role matching role type '%s'";
    private static final String FIND_ALL_SQL_QUERY = "SELECT * FROM roles";
    private static final String FIND_BY_ID_JPQL_QUERY = "SELECT role FROM Role role WHERE role.id=:id";

    private final JdbcTemplate jdbcTemplate;

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
            logMultipleEntitiesOccurrence(roles);
            throw new MultipleRoleMatchingException(msg);
        }
        return roles.stream().findFirst();
    }

    @Override
    public Iterable<Role> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL_QUERY, ((rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setType(RoleType.valueOf(rs.getString("type")));
            return role;
        }));
    }

    @Override
    public Optional<Role> findById(Long id) {
        TypedQuery<Role> query = entityManager.createQuery(FIND_BY_ID_JPQL_QUERY, Role.class);
        query.setParameter("id", id);
        return Optional.of(query.getSingleResult());
    }
}
