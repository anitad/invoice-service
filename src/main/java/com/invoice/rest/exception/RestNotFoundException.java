package com.invoice.rest.exception;

import javax.ws.rs.core.Response;

public class RestNotFoundException extends RestAppException {
    private final static Response.Status status = Response.Status.NOT_FOUND;

    public RestNotFoundException(String code, String message) {
        super(status, new Error(code,status.getStatusCode(),message));
    }
}
