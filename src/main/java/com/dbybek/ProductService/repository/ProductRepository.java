package com.dbybek.ProductService.repository;

import com.dbybek.ProductService.dto.ProductSummary;
import com.dbybek.ProductService.dto.ProductSummaryDTO;
import com.dbybek.ProductService.entity.Product;
import com.dbybek.ProductService.repository.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // ===========================
// JPQL Examples
// ===========================
    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> getProductByCategoryId(@Param("categoryId") Long categoryId);

    //  ===========================
//  Native SQL Examples
//  ===========================
    @Query(value = "select * from product p where p.category_id = :categoryId", nativeQuery = true)
    List<Product> getProductByCategoryIdWithNativeQueries(@Param("categoryId") Long categoryId);

    //  ===========================
//  Projection Examples
//  ===========================
    @Query("select p.title, p.id as id from Product p where p.category.id = :categoryId")
    List<ProductProjection> getProductByCategoryIdUsingProjections(@Param("categoryId") Long categoryId);


//  ==================================
//  Constructor-based DTO Projection
//  ==================================

    @Query("""
            SELECT new com.dbybek.ProductService.dto.ProductSummaryDTO
                (
                    p.title,
                    p.price
                )
            FROM Product p
            """)
    List<ProductSummaryDTO> getProductSummariesUsingDTO();

//  ===============================
//  Interface-based Projection
//  ===============================

    @Query("""
            SELECT p.title AS title, p.price AS price
            FROM Product p
            """)
    List<ProductSummary> getProductSummariesUsingInterface();

//  ===========================
//  Specifications
//  ===========================
//  Dynamic filtering support is provided through
//  JpaSpecificationExecutor<Product>.
}
