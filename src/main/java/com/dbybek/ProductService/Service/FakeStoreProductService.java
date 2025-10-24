package com.dbybek.ProductService.Service;

import com.dbybek.ProductService.Exceptions.ProductNotAvailableException;
import com.dbybek.ProductService.Models.Product;
import com.dbybek.ProductService.dto.FakeStoreProductDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    public FakeStoreProductService (RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotAvailableException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/"+productId,
                FakeStoreProductDto.class
        );

        if(fakeStoreProductDto==null){
            throw new ProductNotAvailableException("Product not found with id: "+productId);
        }
        return fakeStoreProductDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> response = new ArrayList<>();
        FakeStoreProductDto[] fakeStoreAllProducts = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        if(fakeStoreAllProducts == null || fakeStoreAllProducts.length == 0){
            return null;
        }

        for(FakeStoreProductDto prod:fakeStoreAllProducts){
            response.add(prod.toProduct());
        }
        return response;
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

    @Override
    public void deleteSingleProduct(Long productId) {
        restTemplate.delete("https://fakestoreapi.com/products/"+productId);
    }

    @Override
    public Product updateProduct(Long productId,Product product) {
        FakeStoreProductDto fsProduct = new FakeStoreProductDto();
        fsProduct.setId(productId);
        fsProduct.setTitle(product.getTitle());
        fsProduct.setCategory(product.getCategory().getTitle());
        fsProduct.setPrice(product.getPrice());
        fsProduct.setDescription(product.getDescription());
        fsProduct.setImage(product.getImageUrl());

        FakeStoreProductDto response = restTemplate.exchange(
                "https://fakestoreapi.com/products"+productId,
                HttpMethod.PUT,
                new HttpEntity<>(fsProduct),
                FakeStoreProductDto.class
        ).getBody();

        return response!=null?response.toProduct():null;
    }
}
