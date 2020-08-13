package com.invoice.service;


/**
 * Email service interface with set of public
 * apis for performing email operations.
 */
public interface EmailService {

    /**
     * Sends email for one invoice.
     * @param invoiceId
     */
    void sendEmailForOneInvoice(long invoiceId);

    /**
     * Sends email for all the invoices.
     */
    void sendEmailForAllInvoices();
}
