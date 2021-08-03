package com.creseliana.dto;

import com.creseliana.model.Category;
import com.creseliana.model.Image;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AdvertisementCreateRequest {
    @NotNull
    private Category category;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @PositiveOrZero
    private BigDecimal price;
    private List<Image> images;
}
