package com.dbybek.ProductService.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product Service API",
                version = "1.0.0",
                description = "REST API for managing products and categories.",
                contact = @Contact(
                        name = "Bibek Swain"
                )
        )
)
public class OpenApiConfig {
}
