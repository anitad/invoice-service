package com.invoice.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Parent Rest exception. Specific exception will extend
 * from this and provide specific status code.
 */
public class RestAppException extends WebApplicationException {
    public RestAppException(Response.Status status,Error error){
        super(Response.status(status).entity(error).build());
    }
}
