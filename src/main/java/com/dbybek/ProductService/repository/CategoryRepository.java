package com.dbybek.ProductService.repository;

import com.dbybek.ProductService.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByTitle(String title);
}
