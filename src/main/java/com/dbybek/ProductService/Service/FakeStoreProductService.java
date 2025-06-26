package com.dbybek.ProductService.Service;

import com.dbybek.ProductService.Models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    @Override
    public Product getSingleProduct(Long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        System.out.println("We are here.");
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }
}
