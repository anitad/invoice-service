package com.invoice.rest.models;

import java.util.List;

public class Analytics {

    private List<ProductAnalytics> productAnalytics;
    private List<InvoiceAnalytics> invoiceAnalytics;
    private int totalEmailsSent;

    public List<ProductAnalytics> getProductAnalytics() {
        return productAnalytics;
    }

    public void setProductAnalytics(List<ProductAnalytics> productAnalytics) {
        this.productAnalytics = productAnalytics;
    }

    public List<InvoiceAnalytics> getInvoiceAnalytics() {
        return invoiceAnalytics;
    }

    public void setInvoiceAnalytics(List<InvoiceAnalytics> invoiceAnalytics) {
        this.invoiceAnalytics = invoiceAnalytics;
    }

    public int getTotalEmailsSent() {
        return totalEmailsSent;
    }

    public void setTotalEmailsSent(int totalEmailsSent) {
        this.totalEmailsSent = totalEmailsSent;
    }
}

