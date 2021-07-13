package com.creseliana.repository;

import com.creseliana.model.User;

import java.util.Optional;

public interface UserRepository extends ModelRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
