package com.invoice.service;

import com.invoice.rest.models.Invoice;
import com.invoice.rest.models.Invoices;
import com.invoice.rest.models.PageInfo;

/**
 * Invoice service interface with set of public
 * apis for performing invoice operations.
 */
public interface InvoiceService {

    /**
     * Returns the invoice object for
     * a given invoice id.
     * @param invoiceId
     * @return Invoice object.
     */
    Invoice getInvoiceById(long invoiceId);

    /**
     * Returns all the invoices from the
     * database.
     * @return list of Invoice objects.
     */
    Invoices getAllInvoices();

    /**
     * Creates an invoice.
     * @param invoice
     * @return new Invoice object.
     */
    Invoice createInvoice(Invoice invoice);

    /**
     * Return all invoices with paginated results.
     * @param pageInfo
     * @return
     */
    Invoices getAllInvoices(PageInfo pageInfo);
}
