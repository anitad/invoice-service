package com.invoice.rest.exception;

public enum  ErrorCodes {

    INV_404("INV-404"),
    INV_401("INV-401"),
    INV_500("INV-500"),
    INV_400("INV-400");

    private String errorCode;

    ErrorCodes(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
