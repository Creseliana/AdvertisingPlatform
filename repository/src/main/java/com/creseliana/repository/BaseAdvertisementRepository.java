package com.creseliana.repository;

import com.creseliana.model.Advertisement;
import org.springframework.stereotype.Repository;

@Repository
public class BaseAdvertisementRepository extends BaseModelRepository<Advertisement> implements AdvertisementRepository{
    @Override
    protected Class<Advertisement> getModelClass() {
        return Advertisement.class;
    }
}
