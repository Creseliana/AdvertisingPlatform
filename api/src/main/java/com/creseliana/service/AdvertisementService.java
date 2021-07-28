package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementShortResponse;
import com.creseliana.dto.AdvertisementDetailedResponse;
import com.creseliana.dto.AdvertisementPreviewResponse;

import java.util.List;

public interface AdvertisementService {

    void create(String username, AdvertisementCreateRequest newAd);

    void edit(String username, Long id, AdvertisementEditRequest adChanges);

    void delete(String username, Long id);

    void close(String username, Long id);

    void pay(String username, Long id);

    AdvertisementDetailedResponse getById(Long id);

    List<AdvertisementShortResponse> getCompletedByUsername(String username, int page, int amount);

    List<AdvertisementShortResponse> getIncompleteByUsername(String username, int page, int amount);

    List<AdvertisementPreviewResponse> getAll(String category, int page, int amount);
}
