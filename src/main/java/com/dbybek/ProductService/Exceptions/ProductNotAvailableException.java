package com.dbybek.ProductService.Exceptions;

public class ProductNotAvailableException extends Exception {
    public ProductNotAvailableException(String message){
        super(message);
    }
}
