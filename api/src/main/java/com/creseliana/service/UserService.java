package com.creseliana.service;

import com.creseliana.dto.AdvertisementCompletedResponse;
import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserEditRequest;
import com.creseliana.dto.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void create(UserCreateRequest user);

    void edit(String username, UserEditRequest userChanges);

    UserProfileResponse getProfile(String username);

    void rateUser(String username, int rate);

    List<AdvertisementCompletedResponse> getCompletedAds(String username, int start, int amount);
}
