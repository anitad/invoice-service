package com.invoice.service.impl;


import com.invoice.entities.EmailSentEntity;
import com.invoice.repository.EmailSentRepository;
import com.invoice.rest.models.Invoice;
import com.invoice.service.EmailService;
import com.invoice.service.InvoiceService;
import com.invoice.service.exception.EmailServiceException;
import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service("emailService")
@Transactional
public class EmailServiceImpl implements EmailService {

    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private Environment environment;

    @Autowired
    private EmailSentRepository emailSentRepository;

    @Autowired
    private SendGrid sendGrid;


    @Override
    public void sendEmailForOneInvoice(long invoiceId) {
        logger.info("Sending email for invoice, invoiceId = {}",invoiceId);
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        sendEmail(invoice);
        logger.info("Email sent for invoice, invoiceId = {}",invoiceId);
    }

    @Override
    public void sendEmailForAllInvoices() {
        logger.info("Sending email for all invoices..");
        List<Invoice> invoiceList = invoiceService.getAllInvoices().getInvoices();
        for (Invoice invoice : invoiceList) {
            sendEmail(invoice);
        }
    }

    private void sendEmail(Invoice invoice) {
        String emailId = invoice.getCustomer().getEmailId();
        Email from = new Email(environment.getRequiredProperty("sendgrid.from.email"));
        String subject = environment.getRequiredProperty("sendgrid.from.subject");
        Email to = new Email(emailId);
        Content content = new Content("text/plain", "Hello " + invoice.getCustomer().getFirstName() + ", Please find " +
                "your Invoice below:\n" + invoice.toString());
        Mail mail = new Mail(from, subject, to, content);


        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            logger.error("Error while sending email for invoice, invoiceId = {}",invoice.getId(), ex);
            throw new EmailServiceException("Error while sending email for invoice, invoiceId = " + invoice.getId());
        }
        EmailSentEntity emailSentEntity = new EmailSentEntity();
        emailSentEntity.setCustomerId(invoice.getCustomer().getId());
        emailSentEntity.setInvoiceId(invoice.getId());
        emailSentRepository.save(emailSentEntity);
    }



}
