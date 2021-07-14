package com.creseliana.service;

import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserEditRequest;
import com.creseliana.dto.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void create(UserCreateRequest user);

    void edit(String username, UserEditRequest userChanges);

    UserProfileResponse show(String username);
}
