package com.invoice.rest.exception;

import javax.ws.rs.core.Response;

public class RestNotAuthorizedException extends RestAppException{

    private static final Response.Status status = Response.Status.UNAUTHORIZED;

    public RestNotAuthorizedException(String code, String message) {
        super(status,new Error(code,status.getStatusCode(),message));
    }
}
