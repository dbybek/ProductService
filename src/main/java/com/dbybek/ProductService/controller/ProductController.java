package com.dbybek.ProductService.controller;

import com.dbybek.ProductService.dto.*;
import com.dbybek.ProductService.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Products",
        description = "Endpoints for managing products and their categories"
)
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Create a new product",
            description = "Create a new product based on the details provided in the request."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid details provided in the request.")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody CreateProductDTO body) {
        ProductResponseDTO productResponseDTO = productService.createProduct(body);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get a product by ID",
            description = "Retrieves a product using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") Long productId) {
        ProductResponseDTO productResponseDTO = productService.getProductById(productId);
        return ResponseEntity.ok(productResponseDTO);
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all the products."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@ParameterObject
                                                                   @PageableDefault(size = 10, sort = "id")
                                                                   Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @Operation(
            summary = "Search products using optional filters.",
            description = "Retrieves a list of all the products which meet the search filters criteria."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "BAD Request")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProduct(@Parameter(
                                                                          description = "Filter products by title",
                                                                          example = "Dell"
                                                                  )
                                                                  @RequestParam(required = false) String title,

                                                                  @Parameter(
                                                                          description = "Filter products by description",
                                                                          example = "Dell Laptop"
                                                                  )
                                                                  @RequestParam(required = false) String description,

                                                                  @Parameter(
                                                                          description = "Filter products by minimum price",
                                                                          example = "10000.0"
                                                                  )
                                                                  @RequestParam(required = false) Double minPrice,

                                                                  @Parameter(
                                                                          description = "Filter products by maximum price",
                                                                          example = "20000.0"
                                                                  )
                                                                  @RequestParam(required = false) Double maxPrice,

                                                                  @Parameter(
                                                                          description = "Filter products by category",
                                                                          example = "Laptops"
                                                                  )
                                                                  @RequestParam(required = false) String category,

                                                                  @ParameterObject
                                                                  @PageableDefault(size = 10, sort = "id")
                                                                  Pageable pageable
    ) {
        return ResponseEntity.ok(productService.searchProduct(title, description, minPrice, maxPrice, category, pageable));
    }

    @Operation(
            summary = "Update a product by ID",
            description = "Updates an existing product using its unique identifier, based on the details provided in the request."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid details provided in the request."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long productId,
                                                            @Valid @RequestBody UpdateProductDTO productDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(
            summary = "Delete a product by ID",
            description = "Delete a product using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSingleProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product with ID " + productId + " has been successfully deleted.");
    }
}
