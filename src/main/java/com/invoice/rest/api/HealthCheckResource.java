package com.invoice.rest.api;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheckResource {

    @GET
    public Response healthCheck(){
        System.out.println("Inside health check..");
        String message = "Health Check Ok";
        return Response.status(Response.Status.OK).entity(message).build();
    }
}
