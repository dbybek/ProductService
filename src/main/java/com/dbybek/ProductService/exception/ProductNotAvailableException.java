package com.dbybek.ProductService.exception;

public class ProductNotAvailableException extends Exception {
    public ProductNotAvailableException(String message){
        super(message);
    }
}
