package com.creseliana.service;

public interface AdminService {

    void addCategory(String name);

    void blockUser(String username);

    void activateAdminAccount(String username);
}
