package com.dbybek.ProductService.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductDTO {
    private String title;
    private String description;

    @Positive(message = "Price must be greater than 0.")
    private Double price;

    private String imageUrl;
    private String categoryName;
}
