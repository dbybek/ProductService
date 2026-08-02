package com.dbybek.ProductService.service;

import com.dbybek.ProductService.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    ProductResponseDTO getProductById(Long productId);

    Page<ProductResponseDTO> getAllProducts(Pageable pageable);

    Page<ProductResponseDTO> searchProduct(String title, String description, Double minPrice, Double maxPrice, String category, Pageable pageable);

    ProductResponseDTO createProduct(CreateProductDTO dto);

    ProductResponseDTO updateProduct(Long productId, UpdateProductDTO dto);

    void deleteProduct(Long productId);

    List<ProductSummaryDTO> getProductSummariesUsingDTO();

    List<ProductSummary> getProductSummariesUsingInterface();
}
