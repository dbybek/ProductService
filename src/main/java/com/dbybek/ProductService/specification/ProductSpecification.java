package com.dbybek.ProductService.specification;

import com.dbybek.ProductService.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasTitle(String title) {

        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

    public static Specification<Product> hasDescription(String description) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("description")),
                        "%" + description.toLowerCase() + "%"
                );
    }

    public static Specification<Product> priceLessThan(Double price) {
        return (root, query, cb) -> cb.lessThan(root.get("price"), price);
    }

    public static Specification<Product> priceGreaterThan(Double price) {
        return (root, query, cb) -> cb.greaterThan(root.get("price"), price);
    }

    public static Specification<Product> hasCategory(String title) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("category").get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }
}
