package com.creseliana.service;

/**
 * Service for {@link com.creseliana.model.User} entity
 * with role {@link com.creseliana.RoleType#ADMIN}
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
     * @param username the name of the user for blocking
     */
    void blockUser(String username);

    /**
     * Activates user account
     *
     * @param username the name of the user for activating
     */
    void activateAccount(String username);
}
