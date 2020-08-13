package com.invoice.rest.api;


import com.invoice.rest.exception.RestInternalServerException;
import com.invoice.rest.exception.RestNotFoundException;
import com.invoice.rest.models.Analytics;
import com.invoice.rest.models.InvoiceAnalytics;
import com.invoice.rest.models.ProductAnalytics;
import com.invoice.service.AnalyticsService;
import com.invoice.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Rest resources for handling request
 * for analytics data.
 *
 */
@Component
@Path("/analytics")
public class AnalyticsResource {

    @Autowired
    private AnalyticsService analyticsService;

    @GET
    @Path("/product")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getProductAnalytics(){
        try {
            List<ProductAnalytics> productAnalyticsList = analyticsService.getProductAnalytics();
            return Response.status(Response.Status.OK).entity(productAnalyticsList).build();
        }catch(Exception e) {
            e.printStackTrace();
            throw new RestInternalServerException("INV-500", "Error while fetching product analytics");
        }
    }

    @GET
    @Path("/product/{productId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getProductAnalytics(@PathParam("productId")Long productId){
        try {
            ProductAnalytics productAnalytics = analyticsService.getProductAnalyticsForOne(productId);
            return Response.status(Response.Status.OK).entity(productAnalytics).build();
        }catch(ProductNotFoundException ie){
            throw new RestNotFoundException("INV-404",ie.getMessage());
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new RestInternalServerException("INV-500", "Error while fetching product analytics");
        }
    }

    @GET
    @Path("/invoice")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getInvoiceAnalytics(){
        try {
            List<InvoiceAnalytics> invoiceAnalyticsList = analyticsService.getInvoiceAnalytics();
            return Response.status(Response.Status.OK).entity(invoiceAnalyticsList).build();
        }catch(Exception e) {
            e.printStackTrace();
            throw new RestInternalServerException("INV-500", "Error while fetching product analytics");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAnalytics(){
        try {
            Analytics analytics = analyticsService.getAnalytics();
            return Response.status(Response.Status.OK).entity(analytics).build();
        }catch(Exception e) {
            e.printStackTrace();
            throw new RestInternalServerException("INV-500", "Error while fetching product analytics");
        }
    }
}
