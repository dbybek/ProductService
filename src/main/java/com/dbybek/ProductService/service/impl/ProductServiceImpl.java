package com.dbybek.ProductService.service.impl;

import com.dbybek.ProductService.dto.CreateProductDTO;
import com.dbybek.ProductService.dto.ProductResponseDTO;
import com.dbybek.ProductService.dto.UpdateProductDTO;
import com.dbybek.ProductService.entity.Category;
import com.dbybek.ProductService.entity.Product;
import com.dbybek.ProductService.exception.ProductNotAvailableException;
import com.dbybek.ProductService.mapper.ProductMapper;
import com.dbybek.ProductService.repository.CategoryRepository;
import com.dbybek.ProductService.repository.ProductRepository;
import com.dbybek.ProductService.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public ProductServiceImpl (ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) throws ProductNotAvailableException {
//        Optional<Product> product = productRepository.findById(productId);
//        if(product.isEmpty()){
//            throw new ProductNotAvailableException("Product not available.");
//        }
//        return product.get();
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->{ log.warn("Product Not Found/ Not Available");
                        return new ProductNotAvailableException("Product with id "+productId+" not found");});
        return productMapper.toResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts(){
//        List<Product> products = productRepository.findAll();
        return productRepository.findAll().stream().map(productMapper::toResponseDTO).toList();
    }

    @Override
    public ProductResponseDTO createProduct(CreateProductDTO dto){
//        Product product = new Product();
//        product.setTitle(title);
//        product.setDescription(description);
//        product.setPrice(price);
//        product.setImageUrl(imageUrl);
        Product product = productMapper.toEntity(dto);

        var cat = categoryRepository.findByTitle(dto.getCategoryName());
        if(cat==null){
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
    public ProductResponseDTO updateProduct(Long productId, UpdateProductDTO dto) throws ProductNotAvailableException {
//        Optional<Product> optProduct = productRepository.findById(productId);
//        if(optProduct.isEmpty()){
//            throw new ProductNotAvailableException("Product not available");
//        }
        Product updateProduct = productRepository.findById(productId)
                .orElseThrow(() -> { log.warn("Product not found/ not available.");
                        return new ProductNotAvailableException("Product with id "+productId+" not found");});
//        Product updateProduct = optProduct.get();
//        updateProduct.setTitle(title);
//        updateProduct.setDescription(description);
//        updateProduct.setPrice(price);
//        updateProduct.setImageUrl(imageUrl);
        productMapper.updateEntity(updateProduct,dto);
        updateProductCategory(updateProduct, dto.getCategoryName());
        productRepository.save(updateProduct);
        return productMapper.toResponseDTO(updateProduct);
    }

    public void updateProductCategory(Product product, String categoryName){
        if (categoryName == null || categoryName.isBlank()) {
            return;
        }
        var cat = categoryRepository.findByTitle(categoryName.trim());
        if(cat==null){
            cat = new Category(categoryName, new ArrayList<>());
            cat = categoryRepository.save(cat);
        }
        else if(product.getCategory() != null && Objects.equals(cat.getId(), product.getCategory().getId())){
            return;
        }
        var oldCategory = product.getCategory();
//        List<Product> products = oldCategory.getProducts();
//        products.removeIf(p -> p.getId().equals(product.getId()));
        oldCategory.getProducts().removeIf(
                p -> p.getId().equals(product.getId())
        );
//        oldCategory.setProducts(products);
        categoryRepository.save(oldCategory);

//        products = cat.getProducts();
//        products.add(product);
//        cat.setProducts(products);
        product.setCategory(cat);
        cat.getProducts().add(product);
        categoryRepository.save(cat);
    }

    @Override
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }
}
