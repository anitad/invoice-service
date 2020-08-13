package com.invoice.service.impl;

import com.invoice.TestHelper;
import com.invoice.repository.impl.EmailSentRepositoryImpl;
import com.invoice.rest.models.Invoice;
import com.invoice.rest.models.Invoices;
import com.invoice.service.exception.EmailServiceException;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmailServiceImplTest {

    @Mock
    InvoiceServiceImpl invoiceService;

    @Mock
    EmailSentRepositoryImpl emailSentRepository;

    @Mock
    Environment environment;

    @Mock
    SendGrid sendGrid;

    @Mock
    Response response;

    @Mock
    Invoices invoices;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<Invoice> invoiceList = TestHelper.getMockedInvoiceList();
    private Invoice invoice = TestHelper.getMockedInvoice();

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void sendEmailForOneInvoice() throws IOException {
        when(invoiceService.getInvoiceById(invoice.getId())).thenReturn(invoice);
        when(environment.getRequiredProperty("sendgrid.from.email")).thenReturn("test@com");
        when(environment.getRequiredProperty("sendgrid.from.subject")).thenReturn("testsubj");
        when(environment.getRequiredProperty("sendgrid.api.key")).thenReturn("testapikey");
        when(sendGrid.api(any())).thenReturn(response);
        emailService.sendEmailForOneInvoice(invoice.getId());
    }

    @Test
    public void sendEmailForAllInvoices() throws IOException {
        when(invoiceService.getAllInvoices()).thenReturn(invoices);
        when(invoices.getInvoices()).thenReturn(invoiceList);
        when(environment.getRequiredProperty("sendgrid.from.email")).thenReturn("test@com");
        when(environment.getRequiredProperty("sendgrid.from.subject")).thenReturn("testsubj");
        when(environment.getRequiredProperty("sendgrid.api.key")).thenReturn("testapikey");
        when(sendGrid.api(any())).thenReturn(response);
        emailService.sendEmailForAllInvoices();
    }

    @Test
    public void sendEmailForAllInvoicesThrowIOException() throws IOException {
        when(invoiceService.getAllInvoices()).thenReturn(invoices);
        when(invoices.getInvoices()).thenReturn(invoiceList);
        when(environment.getRequiredProperty("sendgrid.from.email")).thenReturn("test@com");
        when(environment.getRequiredProperty("sendgrid.from.subject")).thenReturn("testsubj");
        when(environment.getRequiredProperty("sendgrid.api.key")).thenReturn("testapikey");
        when(sendGrid.api(any())).thenThrow(new IOException());
        /*
        Expected Exception
         */
        expectedException.expect(EmailServiceException.class);
        expectedException.expectMessage("Error while sending email for invoice, invoiceId = " + invoice.getId());
        emailService.sendEmailForAllInvoices();
    }

}