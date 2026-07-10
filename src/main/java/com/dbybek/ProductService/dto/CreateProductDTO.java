package com.dbybek.ProductService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductDTO {
    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than 0.")
    private Double price;

    @NotBlank(message = "Image URL is required.")
    private String imageUrl;

    @NotBlank(message = "Category name is required.")
    private String categoryName;
}
