# Product Service

## Table of Contents

- Overview
- Features
- Architecture
- Technologies Used
- Project Structure
- API Endpoints
- Dynamic Search
- Pagination & Sorting
- Exception Handling
- API Documentation
- Getting Started
- Future Improvements

## Overview

Product Service is a RESTful backend application built with Spring Boot that demonstrates production-oriented backend development practices. It follows a layered architecture using Controllers, Services, Repositories, DTOs, and Mappers to build a clean and maintainable API for managing products and categories.

The application supports CRUD operations, dynamic product search using Spring Data JPA Specifications, pagination, sorting, validation, centralized exception handling, logging, and OpenAPI documentation. It also showcases multiple data access techniques, including Query Derivation, JPQL, Native Queries, DTO Projections, Interface Projections, and Aggregation Queries.

---

# Features

## Product Management

- Create Product
- Retrieve Product by ID
- Retrieve All Products
- Update Product
- Delete Product

## Product Search

- Dynamic search using Spring Data JPA Specifications
- Filter by title
- Filter by description
- Filter by category
- Filter by minimum price
- Filter by maximum price
- Support multiple filter combinations

## Pagination & Sorting

- Pagination using Pageable
- Sorting by any supported field
- Configurable page number and page size

## Data Access

- Spring Data JPA
- Query Derivation 
- JPQL 
- Native SQL 
- Constructor-based DTO Projection 
- Interface-based Projection 
- Aggregation Queries (GROUP BY & HAVING)

## Validation & Exception Handling

- Jakarta Bean Validation
- Global Exception Handling
- Structured Error Responses

## API Documentation & Logging

- Swagger / OpenAPI Documentation
- SLF4J Logging

## Database

- MySQL Integration

---

# Project Architecture
The application follows a layered architecture to ensure **separation of concerns**, **maintainability**, and **scalability**. Each layer has a single responsibility, making the codebase easier to understand, test, and extend.

```text
                HTTP Request
                      │
                      ▼
              ProductController
                      │
                      ▼
             ProductServiceImpl
                      │
        Uses ProductMapper
                      │
          ┌───────────┴───────────┐
          ▼                       ▼
 ProductRepository      CategoryRepository
          │                       │
          └───────────┬───────────┘
                      ▼
                 MySQL Database
```

| Layer          | Responsibility                                                                                                 |
|----------------|----------------------------------------------------------------------------------------------------------------|
| **Controller** | Handles HTTP requests, validates input, and returns HTTP responses.                                            |
| **Service**    | Contains business logic, coordinates repository operations, and manages application workflows.                 |
| **Mapper**     | Converts Entity objects to DTOs and DTOs to Entity objects, keeping the API independent of persistence models. |
| **Repository** | Performs database operations using Spring Data JPA.                                                            |
| **Database**   | Stores Product and Category data in MySQL.                                                                     |

---

# Technologies Used

| Technology              | Purpose                       |
|-------------------------|-------------------------------|
| Java 24                 | Programming Language          |
| Spring Boot 3.5.0       | Backend Application Framework |
| Spring Data JPA         | Data Persistence              |
| MySQL                   | Relational Database           |
| Maven                   | Build & Dependency Management |
| Jakarta Bean Validation | Request Validation            |
| springdoc-openapi       | API Documentation             |
| SLF4J                   | Logging Framework             |

---

# Project Structure

```text
src
└── main
    ├── java
    │   └── com.dbybek.ProductService
    │       ├── controller
    │       │   └── ProductController.java
    │       ├── dto
    │       ├── entity
    │       │   ├── Product.java
    │       │   └── Category.java
    │       ├── exception
    │       ├── mapper
    │       │   └── ProductMapper.java
    │       ├── repository
    │       │   ├── ProductRepository.java
    │       │   └── CategoryRepository.java
    │       ├── service
    │       │   ├── ProductService.java
    │       │   └── impl
    │       │       └── ProductServiceImpl.java
    │       ├── specification
    │       │   └── ProductSpecification.java
    │       └── ProductServiceApplication.java
```

| Package             | Responsibility                                                          |
|---------------------|-------------------------------------------------------------------------|
| controller          | Exposes REST API endpoints and handles HTTP requests and responses.     |
| dto                 | Defines request and response objects exchanged with API clients.        |
| entity              | Contains JPA entity classes mapped to database tables.                  |
| exception           | Defines custom exceptions and the global exception handler.             |
| mapper              | Converts between entities and DTOs using manual mapping.                |
| repository          | Provides data access operations using Spring Data JPA repositories.     |
| service             | Declares business operations through service interfaces.                |
| service.impl        | Implements the business logic for the service layer.                    |
| specification       | Implements reusable Spring Data JPA Specifications for dynamic queries. |

---

# API Endpoints

## Product Management

| Method   | Endpoint         | Description                                       |
|----------|------------------|---------------------------------------------------|
| POST     | `/products`      | Create a new product                              |
| GET      | `/products`      | Retrieve all products with pagination and sorting |
| GET      | `/products/{id}` | Retrieve a product by its ID                      |
| PATCH    | `/products/{id}` | Update an existing product                        |
| DELETE   | `/products/{id}` | Delete a product                                  |

## Product Search

| Method | Endpoint           | Description                                        |
|--------|--------------------|----------------------------------------------------|
| GET    | `/products/search` | Search products dynamically using optional filters |

---

# Dynamic Search using Spring Data JPA Specifications

The Product Service supports dynamic product search using **Spring Data JPA Specifications**.

Instead of creating multiple repository methods or writing complex JPQL queries for every possible search combination, Specifications allow queries to be built dynamically at runtime based on the filters provided by the client.

The `/products/search` endpoint accepts optional query parameters, allowing clients to search using one or more filters in a single request.

## Supported Filters

| Query Parameter | Description                                                 |
|-----------------|-------------------------------------------------------------|
| `title`         | Filter products by title                                    |
| `description`   | Filter products by description                              |
| `category`      | Filter products by category                                 |
| `minPrice`      | Filter products with price greater than the specified value |
| `maxPrice`      | Filter products with price less than the specified value    |

## Example Requests

| Search Scenario            | Request                                                           |
|----------------------------|-------------------------------------------------------------------|
| `Search by title`          | `GET /products/search?title=Dell`                                 |
| `Search by description`    | `GET /products/search?description=Dell laptop`                    |
| `Search by category`       | `GET /products/search?category=Laptops`                           |
| `Search by minPrice`       | `GET /products/search?minPrice=50000`                             |
| `Search by maxPrice`       | `GET /products/search?maxPrice=50000`                             |
| `Combine multiple filters` | `GET /products/search?category=Laptops&title=Dell&minPrice=50000` |

## How it works

The search endpoint combines the provided filters dynamically using Spring Data JPA Specifications.

Each filter is represented as an individual Specification, and only the filters supplied in the request are combined to build the final database query.

This approach keeps the repository clean, reduces code duplication, avoids creating numerous repository query methods, and makes the search functionality easy to extend by adding new Specifications.

---

# Pagination & Sorting

The Product Service supports pagination and sorting using Spring Data's `Pageable` interface. This allows clients to retrieve large datasets efficiently by requesting specific pages and sorting results by supported fields.

> **Default behavior:** Results are returned with a page size of **10** and sorted by **id** unless different values are provided.

## Example Requests

| Description                | Request                                                 |
|----------------------------|---------------------------------------------------------|
| First page (default size)  | `GET /products?page=0`                                  |
| Custom page size           | `GET /products?page=0&size=5`                           |
| Sort by price (ascending)  | `GET /products?sort=price,asc`                          |
| Sort by price (descending) | `GET /products?sort=price,desc`                         |
| Search with pagination     | `GET /products/search?category=Laptops&page=0&size=5`   |
| Search with sorting        | `GET /products/search?category=Laptops&sort=price,desc` |

---

# Exception Handling

The application uses centralized exception handling to provide consistent and meaningful error responses across all REST APIs.

## Handled Exceptions

| Exception                         | Description                                         |
|-----------------------------------|-----------------------------------------------------|
| `ProductNotAvailableException`    | Returned when the requested product does not exist. |
| `MethodArgumentNotValidException` | Returned when request validation fails.             |
| `Exception`                       | Handles unexpected server-side errors.              |

## Error Response

All exceptions return a structured error response containing useful debugging information, including:

- Timestamp
- HTTP Status
- Error Message
- Validation Errors (when applicable)
- Request Path

## Sample Error Response

```json
{
    "timestamp": "2026-08-02T14:43:03.758234",
    "status": 404,
    "error": "Not Found",
    "message": "Product with id 100 not found",
    "path": "/products/100"
}
```
---

# Swagger / OpenAPI Documentation

The Product Service integrates **Swagger UI** using **springdoc-openapi** to provide interactive API documentation.

The generated documentation includes:

- Available REST endpoints
- Request and response models
- Query parameters
- HTTP status codes
- API descriptions using OpenAPI annotations

Once the application is running, the documentation can be accessed at:

**Swagger UI**

```text
http://localhost:8080/swagger-ui/index.html
```

**OpenAPI Specification**

```text
http://localhost:8080/v3/api-docs
```

---

# Getting Started

## Prerequisites

Before running the application, ensure the following are installed:

- Java 24
- Maven
- MySQL
- Git (optional, for cloning the repository)

---

## Clone the Repository

```bash
git clone <https://github.com/dbybek/ProductService.git>
cd ProductService
```

---

## Configure the Database

Update the database configuration in `src/main/resources/application.properties`.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/product_service
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Ensure that the MySQL database has been created before starting the application.

---

## Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or run `ProductServiceApplication` directly from your IDE.

---

## Access the Application

After the application starts successfully:

| Resource     | URL                                           |
|--------------|-----------------------------------------------|
| Swagger UI   | `http://localhost:8080/swagger-ui/index.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs`           |

---

# Future Improvements

Possible enhancements for the project include:

- Implement JWT-based Authentication and Role-Based Authorization
- Add Unit and Integration Tests
- Introduce caching for frequently accessed data
- Implement CI/CD using GitHub Actions
- Add API versioning
- Add monitoring and health checks using Spring Boot Actuator

---

# Author

Bibek Swain