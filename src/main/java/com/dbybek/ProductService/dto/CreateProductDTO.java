package com.dbybek.ProductService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductDTO {
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String categoryName;
}
