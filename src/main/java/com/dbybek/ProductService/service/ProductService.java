package com.dbybek.ProductService.service;

import com.dbybek.ProductService.dto.CreateProductDTO;
import com.dbybek.ProductService.dto.ProductResponseDTO;
import com.dbybek.ProductService.dto.UpdateProductDTO;
import com.dbybek.ProductService.exception.ProductNotAvailableException;

import java.util.List;

public interface ProductService {
    ProductResponseDTO getProductById(Long productId) throws ProductNotAvailableException;
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO createProduct(CreateProductDTO dto);
    ProductResponseDTO updateProduct(Long productId, UpdateProductDTO dto) throws ProductNotAvailableException;
    void deleteProduct(Long productId);
}
