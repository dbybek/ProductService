package com.dbybek.ProductService.Repositories;

import com.dbybek.ProductService.Models.Product;
import com.dbybek.ProductService.Repositories.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    Product findByTitle(String title);
    Product findByDescription(String description);

//  How to implement HQL(Hibernate Query Language)
    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> getProductByCategoryId(@Param("categoryId") Long categoryId);

//  How to implement native queries
    @Query(value = "select * from product p where p.category_id = :categoryId", nativeQuery = true)
    List<Product> getProductByCategoryIdWithNativeQueries(@Param("categoryId") Long categoryId);

//  HQL with projections
//  Projections: Allows you to fetch certain specific columns from the database.
    @Query("select p.title, p.id as id from Product p where p.category.id = :categoryId")
    List<ProductProjection> getProductByCategoryIdUsingProjections(@Param("categoryId") Long categoryId);
}
