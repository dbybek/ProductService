package com.dbybek.ProductService.service.impl;

import com.dbybek.ProductService.dto.*;
import com.dbybek.ProductService.entity.Category;
import com.dbybek.ProductService.entity.Product;
import com.dbybek.ProductService.exception.ProductNotAvailableException;
import com.dbybek.ProductService.mapper.ProductMapper;
import com.dbybek.ProductService.repository.CategoryRepository;
import com.dbybek.ProductService.repository.ProductRepository;
import com.dbybek.ProductService.service.ProductService;
import com.dbybek.ProductService.specification.ProductSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Get failed. Product with id {} not found.", productId);
                    return new ProductNotAvailableException("Product with id " + productId + " not found");
                });
        return productMapper.toResponseDTO(product);
    }


    @Override
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponseDTO);
    }

    @Override
    public Page<ProductResponseDTO> searchProduct(String title,
                                                  String description,
                                                  Double minPrice,
                                                  Double maxPrice,
                                                  String category,
                                                  Pageable pageable) {
        Specification<Product> spec = Specification.where(null);
        if (title != null && !title.isBlank()) {
            spec = spec.and(ProductSpecification.hasTitle(title.trim()));
        }

        if (description != null && !description.isBlank()) {
            spec = spec.and(ProductSpecification.hasDescription(description.trim()));
        }

        if (minPrice != null) {
            spec = spec.and(ProductSpecification.priceGreaterThan(minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.priceLessThan(maxPrice));
        }

        if (category != null && !category.isBlank()) {
            spec = spec.and(ProductSpecification.hasCategory(category.trim()));
        }

        return productRepository.findAll(spec, pageable).map(productMapper::toResponseDTO);
    }

    @Override
    public List<ProductSummaryDTO> getProductSummariesUsingDTO() {
        return productRepository.getProductSummariesUsingDTO();
    }

    @Override
    public List<ProductSummary> getProductSummariesUsingInterface() {
        return productRepository.getProductSummariesUsingInterface();
    }

    @Override
    public ProductResponseDTO createProduct(CreateProductDTO dto) {
        Product product = productMapper.toEntity(dto);

        var cat = categoryRepository.findByTitle(dto.getCategoryName());
        if (cat == null) {
            cat = new Category(dto.getCategoryName(), new ArrayList<>());
        }
        product.setCategory(cat);
        List<Product> products = cat.getProducts();
        products.add(product);
        cat.setProducts(products);
        categoryRepository.save(cat);
        productRepository.save(product);
        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, UpdateProductDTO dto) {
        Product updateProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Update failed. Product with id {} not found in the database.", productId);
                    return new ProductNotAvailableException("Product with id " + productId + " not found");
                });

        productMapper.updateEntity(updateProduct, dto);
        updateProductCategory(updateProduct, dto.getCategoryName());
        productRepository.save(updateProduct);
        return productMapper.toResponseDTO(updateProduct);
    }

    public void updateProductCategory(Product product, String categoryName) {
        categoryName = categoryName == null ? null : categoryName.trim();
        if (categoryName == null || categoryName.isBlank()) {
            return;
        }
        var cat = categoryRepository.findByTitle(categoryName);
        if (cat == null) {
            cat = new Category(categoryName, new ArrayList<>());
            cat = categoryRepository.save(cat);
        } else if (product.getCategory() != null && Objects.equals(cat.getId(), product.getCategory().getId())) {
            return;
        }
        var oldCategory = product.getCategory();
        oldCategory.getProducts().removeIf(
                p -> p.getId().equals(product.getId())
        );
        categoryRepository.save(oldCategory);
        product.setCategory(cat);
        cat.getProducts().add(product);
        categoryRepository.save(cat);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Delete failed. Product with id {} not found in database.", productId);
                    return new ProductNotAvailableException(
                            "Product with id " + productId + " not found");
                });
        productRepository.delete(product);
    }
}
