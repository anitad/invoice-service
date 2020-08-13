package com.invoice.rest.exception;

import javax.ws.rs.core.Response;

public class RestInternalServerException extends RestAppException {
    private static final Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

    public RestInternalServerException(String code, String message) {
        super(status,new Error(code,status.getStatusCode(),message));
    }

}
