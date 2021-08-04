package com.creseliana.dto;

import com.creseliana.model.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * DTO of the {@link com.creseliana.model.Advertisement}
 * Useful for edit existing advertisement information
 * All fields must be filled with old or new data
 */
@Getter
@Setter
public class AdvertisementEditRequest {
    @NotEmpty
    @NotBlank
    private Category category;
    @NotEmpty
    @NotBlank
    private String title;
    @NotEmpty
    @NotBlank
    private String description;
    @NotEmpty
    @NotBlank
    private BigDecimal price;
}
