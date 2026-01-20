package com.dbybek.ProductService;

import com.dbybek.ProductService.Models.Category;
import com.dbybek.ProductService.Models.Product;
import com.dbybek.ProductService.Repositories.CategoryRepository;
import com.dbybek.ProductService.Repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceApplicationTests {

	@Autowired
	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testingQueries(){
/*
		List<Product> products = productRepository.getProductByCategoryId(1L);
		System.out.println(products.get(0));
*/

        List<Product> products = productRepository.getProductByCategoryIdWithNativeQueries(1L);
		System.out.println(products.get(0));
	}

	@Test
	void fetchCategoryLazy(){
		Category cat = categoryRepository.findById(1L).get();
		System.out.println(cat.getId());
		System.out.println("We are done here.");

		List<Product> catProducts = cat.getProducts();
		System.out.println(catProducts);
		System.out.println("Products fetched.");
	}

}
