package com.dbybek.ProductService.Controller;

import com.dbybek.ProductService.Service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Annotation for letting JVM know where all the API end points are there
public class ProductController {

    //POST /Product
    //Request Body
//    {
//        "id":0,
//            "title": "1More Piston Fit Earphones",
//            "price": 2000.0,
//            "description": "Metallic Earphones with active Noise cancellation",
//            "category": "Electronic",
//            "image": "http://example.com"
//    }

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products") /* This is my API end point or API url "/products".
                                 Whenever someone is hitting /products with post request
                                 please execute the below method.
                              */
    public void createProduct() {

    }

    @GetMapping("/products/{id}")/* This is my API end point or API url "/products/{id}".
                                    Whenever someone is doing a get request on /products/{id}
                                    please execute the below method.
                                 */
    public void getProduct(@PathVariable("id") Long productId) {

    }

    @GetMapping("/products")/* This is my API end point or API url "/products/{id}".
                               Whenever someone is doing a get request on /products
                               please execute the below method.
                            */
    public void getAllProduct() {
        productService.getAllProducts();
    }
}
