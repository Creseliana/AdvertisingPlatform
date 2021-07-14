package com.creseliana.dto;

import com.creseliana.model.Comment;
import com.creseliana.model.Image;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AdvertisementShowResponse {
    private String authorName;
    private String authorPhoneNumber;
    private String categoryName;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private List<Image> images;
    private List<Comment> comments;
}
