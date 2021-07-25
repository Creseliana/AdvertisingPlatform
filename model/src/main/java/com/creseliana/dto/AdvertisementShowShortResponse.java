package com.creseliana.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdvertisementShowShortResponse {
    private Long id;
    private String authorFirstName;
    private BigDecimal authorRating;
    private String categoryName;
    private String title;
    private BigDecimal price;
    private LocalDateTime creationDate;
}
