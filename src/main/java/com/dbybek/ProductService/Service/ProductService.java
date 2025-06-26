package com.dbybek.ProductService.Service;

import com.dbybek.ProductService.Models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId);
    List<Product> getAllProducts();
    Product createProduct(Product product);
}
