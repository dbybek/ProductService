package com.dbybek.ProductService.Controller;

import com.dbybek.ProductService.Exceptions.ProductNotAvailableException;
import com.dbybek.ProductService.Models.Product;
import com.dbybek.ProductService.Service.ProductService;
import com.dbybek.ProductService.dto.ErrorDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    public ProductController(@Qualifier("selfProductService") ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products") /* This is my API end point or API url "/products".
                                 Whenever someone is hitting /products with post request
                                 please execute the below method.
                              */
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/products/{id}")/* This is my API end point or API url "/products/{id}".
                                    Whenever someone is doing a get request on /products/{id}
                                    please execute the below method.
                                 */
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId) throws ProductNotAvailableException {
        Product currentProduct = productService.getSingleProduct(productId);
        ResponseEntity<Product> res = new ResponseEntity<>(
                currentProduct, HttpStatus.OK);
        return res;
    }

    @GetMapping("/products")/* This is my API end point or API url "/products/{id}".
                               Whenever someone is doing a get request on /products
                               please execute the below method.
                            */
    public List<Product> getAllProduct() {
        return productService.getAllProducts();
    }

    @PutMapping("/products/{id}")/* This is my API end point or API url "/products/{id}".
                                    Whenever someone is doing a put request on /products/{id}
                                    please execute the below method.
                                 */
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId) throws ProductNotAvailableException {
        Product currentProduct = productService.updateProduct(productId,getProduct(productId).getBody());
        ResponseEntity<Product> res = new ResponseEntity<>(currentProduct, HttpStatus.OK);
        return res;
    }

    @DeleteMapping("/products/{id}")/* This is my API end point or url "/products/{id}".
                                       Whenever someone is doing a delete request on /products/{id}
                                       please execute the below method.
                                    */
    public ResponseEntity<String> deleteSingleProduct(@PathVariable("id") Long productId) {
        productService.deleteSingleProduct(productId);
        return ResponseEntity.ok("Product ID: "+productId+" has been successfully deleted.");
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<ErrorDto> handleProductNotAvailableException (Exception e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());

        return new ResponseEntity<>(errorDto,HttpStatus.NOT_FOUND);
    }
}
