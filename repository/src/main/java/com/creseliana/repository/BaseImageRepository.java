package com.creseliana.repository;

import com.creseliana.model.Image;
import org.springframework.stereotype.Repository;

@Repository
public class BaseImageRepository extends BaseModelRepository<Image> implements ImageRepository {
    @Override
    protected Class<Image> getModelClass() {
        return Image.class;
    }
}
