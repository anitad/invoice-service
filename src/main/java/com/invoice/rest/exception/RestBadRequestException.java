package com.invoice.rest.exception;


import javax.ws.rs.core.Response;


public class RestBadRequestException extends RestAppException {

    private static final Response.Status status = Response.Status.BAD_REQUEST;

    public RestBadRequestException(String code, String message) {
        super(status, new Error(code, status.getStatusCode(),message));
    }
}
