package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementResponse;
import com.creseliana.dto.AdvertisementShowResponse;
import com.creseliana.dto.AdvertisementShowShortResponse;

import java.util.List;

public interface AdvertisementService {

    void create(String username, AdvertisementCreateRequest newAd);

    void edit(String username, Long id, AdvertisementEditRequest adChanges);

    void delete(String username, Long id);

    void close(String username, Long id);

    void pay(String username, Long id);

    AdvertisementShowResponse getById(Long id);

    List<AdvertisementResponse> getCompletedByUsername(String username, int page, int amount);

    List<AdvertisementResponse> getIncompleteByUsername(String username, int page, int amount);

    AdvertisementShowShortResponse getAll(String category, int page, int amount);
}
