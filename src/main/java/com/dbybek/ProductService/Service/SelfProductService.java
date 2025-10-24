package com.dbybek.ProductService.Service;

import com.dbybek.ProductService.Exceptions.ProductNotAvailableException;
import com.dbybek.ProductService.Models.Category;
import com.dbybek.ProductService.Models.Product;
import com.dbybek.ProductService.Repositories.CategoryRepository;
import com.dbybek.ProductService.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotAvailableException {
        Optional<Product> p = productRepository.findById(productId);
        if(p.isPresent()){
            return p.get();
        }
        throw new ProductNotAvailableException("Product not found.");
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        Category cat = categoryRepository.findByTitle(product.getCategory().getTitle());
        if(cat==null){
            // No category with our title in the database. So we create a new category.
            Category newCat = new Category();
            newCat.setTitle(product.getCategory().getTitle());
            Category newRow = categoryRepository.save(newCat);
            product.setCategory(newRow);
        }
        else{
            product.setCategory(cat);
        }

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public void deleteSingleProduct(Long productId) {

    }
}
