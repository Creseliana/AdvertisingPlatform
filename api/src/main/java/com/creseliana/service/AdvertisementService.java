package com.creseliana.service;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementShowResponse;

public interface AdvertisementService {

    void create(String username, AdvertisementCreateRequest newAd);

    void edit(String username, Long id, AdvertisementEditRequest adChanges);

    void delete(String username, Long id);

    void close(String username, Long id);

    AdvertisementShowResponse show(Long id);
}
