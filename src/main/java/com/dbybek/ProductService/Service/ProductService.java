package com.dbybek.ProductService.Service;

import com.dbybek.ProductService.Exceptions.ProductNotAvailableException;
import com.dbybek.ProductService.Models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotAvailableException;
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Long productId,Product product);
    void deleteSingleProduct(Long productId);
}
