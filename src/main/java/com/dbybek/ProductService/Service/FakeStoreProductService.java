package com.dbybek.ProductService.Service;

import com.dbybek.ProductService.Models.Product;
import com.dbybek.ProductService.dto.FakeStoreProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    public FakeStoreProductService (RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/"+productId,
                FakeStoreProductDto.class
        );

        return fakeStoreProductDto!=null?fakeStoreProductDto.toProduct():null;
    }

    @Override
    public List<Product> getAllProducts() {
        System.out.println("We are here.");
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fsProduct = new FakeStoreProductDto();
        fsProduct.setId(product.getId());
        fsProduct.setTitle(product.getTitle());
        fsProduct.setCategory(product.getCategory().getTitle());
        fsProduct.setPrice(product.getPrice());
        fsProduct.setDescription(product.getDescription());
        fsProduct.setImage(product.getImageUrl());

        FakeStoreProductDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fsProduct,
                FakeStoreProductDto.class
        );

        return response!=null?response.toProduct():null;
    }
}
