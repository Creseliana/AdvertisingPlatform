package com.creseliana.service;

import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserEditRequest;
import com.creseliana.dto.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service for {@link com.creseliana.model.User}
 */
public interface UserService extends UserDetailsService {

    /**
     * Creates new user with given role
     *
     * @param user the DTO of the {@link com.creseliana.model.User}
     *             with information about new user
     * @param role the string name of the role type
     */
    void create(UserCreateRequest user, String role);

    /**
     * Edits existing user's information
     *
     * @param username the name of the user for editing
     * @param userChanges the DTO of the {@link com.creseliana.model.User}
     *                    with new information
     */
    void edit(String username, UserEditRequest userChanges);

    /**
     * Gets user by its username
     *
     * @param username the name of the expected user
     * @return the DTO of the {@link com.creseliana.model.User}
     */
    UserProfileResponse getByUsername(String username);
}
