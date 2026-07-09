package com.dbybek.ProductService.mapper;

import com.dbybek.ProductService.dto.CreateProductDTO;
import com.dbybek.ProductService.dto.ProductResponseDTO;
import com.dbybek.ProductService.dto.UpdateProductDTO;
import com.dbybek.ProductService.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(CreateProductDTO dto){
        if(dto == null){
            return null;
        }
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());

        return product;
    }

    public ProductResponseDTO toResponseDTO(Product product){
        if(product == null){
            return null;
        }

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getTitle());
        }

        return dto;
    }

    // TODO: Remove these validation checks after implementing Jakarta Bean Validation (@Valid).
    public void updateEntity(Product product, UpdateProductDTO dto){
        if(dto == null){
            return;
        }
        if(dto.getTitle()!=null && !dto.getTitle().isBlank()){
            product.setTitle(dto.getTitle());
        }
        if(dto.getDescription()!=null && !dto.getDescription().isBlank()){
            product.setDescription(dto.getDescription());
        }
        if(dto.getPrice()!=null && !dto.getPrice().isInfinite() && !dto.getPrice().isNaN()){
            product.setPrice(dto.getPrice());
        }
        if(dto.getImageUrl()!=null && !dto.getImageUrl().isBlank()){
            product.setImageUrl(dto.getImageUrl());
        }
    }
}