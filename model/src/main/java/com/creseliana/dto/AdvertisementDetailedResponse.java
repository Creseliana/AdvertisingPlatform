package com.creseliana.dto;

import com.creseliana.model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDetailedResponse {
    private String authorFirstName;
    private String authorPhoneNumber;
    private BigDecimal authorRating;
    private String categoryName;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private List<Image> images;
}
