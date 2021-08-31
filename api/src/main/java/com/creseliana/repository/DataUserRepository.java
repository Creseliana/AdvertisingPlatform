package com.creseliana.repository;

import com.creseliana.dto.UserPreviewShort;
import com.creseliana.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DataUserRepository extends PagingAndSortingRepository<User, Long> {

    @Query(value = "SELECT COUNT(user) FROM User user WHERE user.isActive = TRUE")
    long countActiveUsers();

    @EntityGraph(value = "roles-user-graph")
    Optional<User> findByUsername(String username);

    UserPreviewShort findByEmail(String email);
}
