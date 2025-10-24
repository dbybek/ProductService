package com.dbybek.ProductService.Repositories;

import com.dbybek.ProductService.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    Product findByTitle(String title);
}
