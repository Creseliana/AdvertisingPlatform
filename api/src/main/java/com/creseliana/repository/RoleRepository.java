package com.creseliana.repository;

import com.creseliana.RoleType;
import com.creseliana.model.Role;

import java.util.Optional;

public interface RoleRepository extends ModelRepository<Role, Long> {

    Optional<Role> getRoleByType(RoleType type);
}
