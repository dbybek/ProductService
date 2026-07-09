package com.dbybek.ProductService.controller;

import com.dbybek.ProductService.dto.CreateProductDTO;
import com.dbybek.ProductService.dto.ProductResponseDTO;
import com.dbybek.ProductService.dto.UpdateProductDTO;
import com.dbybek.ProductService.exception.ProductNotAvailableException;
import com.dbybek.ProductService.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Annotation for letting JVM know where all the API end points are there
@RequestMapping("/products")
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

    @PostMapping("") /* This is my API end point or API url "/products".
                                 Whenever someone is hitting /products with post request
                                 please execute the below method.
                              */
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody CreateProductDTO body) {
        ProductResponseDTO productResponseDTO = productService.createProduct(body);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")/* This is my API end point or API url "/products/{id}".
                                    Whenever someone is doing a get request on /products/{id}
                                    please execute the below method.
                                 */
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") Long productId) throws ProductNotAvailableException {
//        Product currentProduct = productService.getProductById(productId);
//        ResponseEntity<Product> res = new ResponseEntity<>(
//                currentProduct, HttpStatus.OK);
        ProductResponseDTO productResponseDTO = productService.getProductById(productId);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @GetMapping("")/* This is my API end point or API url "/products/{id}".
                               Whenever someone is doing a get request on /products
                               please execute the below method.
                            */
    public ResponseEntity<List<ProductResponseDTO>> getAllProduct() {
        List<ProductResponseDTO> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @PatchMapping("/{id}")/* This is my API end point or API url "/products/{id}".
                                    Whenever someone is doing a put request on /products/{id}
                                    please execute the below method.
                                 */
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long productId, @RequestBody UpdateProductDTO productDTO) throws ProductNotAvailableException {
        ProductResponseDTO updatedProduct = productService.updateProduct(productId,productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")/* This is my API end point or url "/products/{id}".
                                       Whenever someone is doing a delete request on /products/{id}
                                       please execute the below method.
                                    */
    public ResponseEntity<String> deleteSingleProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product ID: "+productId+" has been successfully deleted.");
    }
}
