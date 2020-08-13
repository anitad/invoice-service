package com.invoice.rest.api;

import com.intuit.invoice.rest.exception.*;
import com.intuit.invoice.rest.models.*;
import com.invoice.rest.exception.*;
import com.invoice.service.InvoiceService;
import com.invoice.service.exception.ProductInvalidException;
import com.invoice.service.exception.InvoiceNotFoundException;
import com.invoice.rest.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rest resource for handling invoice
 * operations.
 */
@Component
@Path("/invoices")
public class InvoiceResource {

    private Logger logger = LoggerFactory.getLogger(InvoiceResource.class);
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    @Autowired
    InvoiceService invoiceService;
/*
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllInvoices(){
        try {
            List<Invoice> invoices = invoiceService.getAllInvoices();
            return Response.status(Response.Status.OK).entity(invoices).build();
        } catch(Exception e){
            logger.error("Error while getting invoice..",e);
            throw new RestInternalServerException("INV-500","Error while fetching invoice");
        }
    }
*/
    @GET
    @Path("/{invoiceId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getInvoice(@PathParam("invoiceId")Long invoiceId ){
        try {
            Invoice invoice = invoiceService.getInvoiceById(invoiceId);
            return Response.status(Response.Status.OK).entity(invoice).build();
        }catch(InvoiceNotFoundException ie){
            logger.error("Invoice not found, invoiceId = {}",invoiceId);
            throw new RestNotFoundException("INV-404", ie.getMessage());
        }
        catch(Exception e){
            logger.error("Error while getting invoice..",e);
            throw new RestInternalServerException("INV-500","Error while fetching invoice");
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response createInvoice(Invoice invoice){
        try {
            validateInvoice(invoice);
            invoice = invoiceService.createInvoice(invoice);
            return Response.status(Response.Status.OK).entity(invoice).build();
        } catch (RestAppException e) {
            throw e;
        }
        catch (ProductInvalidException ex) {
            throw new RestNotFoundException("INV-400", ex.getMessage());
        } catch(Exception e){
            logger.error("Error while creating invoice..",e);
            throw new RestInternalServerException("INV-500", "Error while creating Invoice");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllInvoices(@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit){
        try {
            PageInfo pageInfo = null;
            Invoices invoices = null;
            //note: both offset and limit have to be provided by the client together. Either one missing then the call is treated regular call, without pageinfo.
            if (offset != null && limit != null) {
                 pageInfo = new PageInfo(offset,limit);
                 invoices = invoiceService.getAllInvoices(pageInfo);
            } else {
             invoices = invoiceService.getAllInvoices();
            }
            return Response.status(Response.Status.OK).entity(invoices).build();
        } catch(Exception e){
            logger.error("Error while getting invoice..",e);
            throw new RestInternalServerException("INV-500","Error while fetching invoice");
        }
    }

    private void validateInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new RestBadRequestException(ErrorCodes.INV_400.getErrorCode(),"Invoice cannot be empty.");
        }
        if (invoice.getCustomer() == null) {
            throw new RestBadRequestException(ErrorCodes.INV_400.getErrorCode(),"Customer cannot be null.");
        }
        if (StringUtils.isEmpty(invoice.getCustomer().getEmailId())) {
            throw new RestBadRequestException(ErrorCodes.INV_400.getErrorCode(),"Email cannot be null.");
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(invoice.getCustomer().getEmailId());
        if (! matcher.matches()) {
            throw new RestBadRequestException(ErrorCodes.INV_400.getErrorCode(),"Invalid email address.");
        }

        if (invoice.getCustomer().getAddresses() == null || invoice.getCustomer().getAddresses().size() == 0 ) {
            throw new RestBadRequestException(ErrorCodes.INV_400.getErrorCode(),"Address cannot be null.");
        }
        for (Address address : invoice.getCustomer().getAddresses()) {
            if (StringUtils.isEmpty(address.getStreet()) ||
                StringUtils.isEmpty(address.getCity()) ||
                StringUtils.isEmpty(address.getState()) ||
                StringUtils.isEmpty(address.getZipCode()) ||
                StringUtils.isEmpty(address.getCountry())) {
                throw new RestBadRequestException(ErrorCodes.INV_400.getErrorCode(),"Invalid address.");
            }
        }
        if (invoice.getLineItems() == null || invoice.getLineItems().size() == 0) {
            throw new RestBadRequestException(ErrorCodes.INV_400.getErrorCode(),"Line items cannot be null.");
        }
        for (LineItem lineItem : invoice.getLineItems()) {
            if (lineItem == null || lineItem.getProduct() == null ||
                    StringUtils.isEmpty(lineItem.getProduct().getName()) || lineItem.getQuantity() == 0){
                throw new RestBadRequestException(ErrorCodes.INV_400.getErrorCode(),"Line must contain products.");
            }

        }
    }
}
