package com.invoice.service.exception;

public class ProductInvalidException extends RuntimeException {

    public ProductInvalidException(String message) {
        super(message);
    }
}
