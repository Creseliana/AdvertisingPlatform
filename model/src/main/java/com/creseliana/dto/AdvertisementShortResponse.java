package com.creseliana.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdvertisementShortResponse {
    private String categoryName;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDateTime creationDate;
}
