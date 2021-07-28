package com.creseliana.service;

/**
 * Service for user entity with role {@link com.creseliana.RoleType#ADMIN}
 */
public interface AdminService {

    /**
     * Adds new category
     *
     * @param name the category name
     */
    void addCategory(String name);

    /**
     * Blocks user by username
     *
     * @param username unique username of the user
     */
    void blockUser(String username);

    /**
     * Activates user account
     *
     * @param username unique username of the user
     */
    void activateAccount(String username);
}
