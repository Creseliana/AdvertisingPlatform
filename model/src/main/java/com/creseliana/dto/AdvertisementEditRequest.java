package com.creseliana.dto;

import com.creseliana.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdvertisementEditRequest {
    private Category category;
    private String title;
    private String description;
    private BigDecimal price;
}
