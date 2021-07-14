package com.creseliana.dto;

import com.creseliana.model.Category;
import com.creseliana.model.Image;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AdvertisementCreateRequest {
    private Category category;
    private String title;
    private String description;
    private BigDecimal price;
    private List<Image> images;
}
