package com.dbybek.ProductService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSummaryDTO {
    private String title;
    private Double price;
}
