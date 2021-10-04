package com.creseliana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementPreviewResponse {
    private Long id;
    private String authorFirstName;
    private BigDecimal authorRating;
    private String categoryName;
    private String title;
    private BigDecimal price;
    private LocalDateTime creationDate;
}
