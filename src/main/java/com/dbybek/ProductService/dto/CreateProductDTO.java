package com.dbybek.ProductService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(
            description = "Product title",
            example = "1More Piston Fit Earphones"
    )
    @NotBlank(message = "Title is required.")
    private String title;

    @Schema(
            description = "Detailed description of the product",
            example = "Metallic earphones with active noise cancellation"
    )
    @NotBlank(message = "Description is required.")
    private String description;

    @Schema(
            description = "Price of the product",
            example = "2000.0"
    )
    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than 0.")
    private Double price;

    @Schema(
            description = "URL of the product image",
            example = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnHLam43brtr5ug9C7ZHQPXMGdadalojXJseNObGCHMw&s=10"
    )
    @NotBlank(message = "Image URL is required.")
    private String imageUrl;

    @Schema(
            description = "Category to which the product belongs",
            example = "Electronics"
    )
    @NotBlank(message = "Category name is required.")
    private String categoryName;
}
