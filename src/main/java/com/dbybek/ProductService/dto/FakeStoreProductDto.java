package com.dbybek.ProductService.dto;

import com.dbybek.ProductService.Models.Category;
import com.dbybek.ProductService.Models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;

    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        Category cat = new Category();
        cat.setTitle(category);
        product.setCategory(cat);

        return product;
    }
}
