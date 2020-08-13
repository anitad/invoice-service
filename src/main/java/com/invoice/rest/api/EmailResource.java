package com.invoice.rest.api;

import com.invoice.rest.exception.RestInternalServerException;
import com.invoice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Email resource for handling
 * email requests.
 */
@Component
@Path("/emails")
public class EmailResource {
    @Autowired
    EmailService emailService;

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    public Response sendEmail(@QueryParam("invoiceId")Long invoiceId) {
        try {
            if (invoiceId != null) {
                emailService.sendEmailForOneInvoice(invoiceId);
            } else {
                emailService.sendEmailForAllInvoices();
            }
            return Response.status(Response.Status.ACCEPTED).entity("Email request accepted.").build();
        } catch (Exception e) {
            throw new RestInternalServerException("INV-500","Error while sending email, invoiceId = " +  invoiceId);
        }
    }

}
