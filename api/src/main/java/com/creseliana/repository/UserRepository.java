package com.creseliana.repository;

import com.creseliana.model.User;

import java.util.Optional;

public interface UserRepository extends ModelRepository<User, Long> {

    Optional<User> findByUserName(String userName);
}
