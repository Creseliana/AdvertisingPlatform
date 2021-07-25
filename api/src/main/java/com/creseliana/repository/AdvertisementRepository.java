package com.creseliana.repository;

import com.creseliana.model.Advertisement;

import java.util.List;

public interface AdvertisementRepository extends ModelRepository<Advertisement, Long> {

    List<Advertisement> getAdsByClosedAndAuthorId(boolean isClosed, Long id, int start, int amount);

    List<Advertisement> getPayedAdsOrderByDate(int start, int amount);
}
