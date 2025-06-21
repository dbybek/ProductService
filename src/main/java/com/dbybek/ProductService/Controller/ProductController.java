package com.dbybek.ProductService.Controller;

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

    @PostMapping("/products") /* This is my API end point or API url "/products".
                                 Whenever someone is hitting /products with post request please execute the below method.
                              */
    public void createProduct() {

    }
}
