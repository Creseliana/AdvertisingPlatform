package com.creseliana.repository;

import com.creseliana.model.Advertisement;

import java.util.List;

public interface AdvertisementRepository extends ModelRepository<Advertisement, Long> {

    List<Advertisement> getCompletedAdsByUserId(Long id, int start, int amount);
}
