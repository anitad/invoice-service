package com.invoice.service;

import com.invoice.rest.models.Analytics;
import com.invoice.rest.models.InvoiceAnalytics;
import com.invoice.rest.models.ProductAnalytics;

import java.util.List;

/**
 * Analytics service interface with set of public
 * apis for performing analytics operations.
 */
public interface AnalyticsService {

    /**
     * Returns the product analytics data.
     * @return List of product aggregated data.
     */
    List<ProductAnalytics> getProductAnalytics();

    /**
     * Returns product analytics data for given
     * product.
     * @param productId
     * @return product aggregated data.
     */
    ProductAnalytics getProductAnalyticsForOne(long productId);

    /**
     * Returns invoice analytics data.
     * @return List of invoice aggregated data.
     */
    List<InvoiceAnalytics> getInvoiceAnalytics();

    /**
     * Return the aggregated data for product,
     * invoice and email.
     * @return
     */
    Analytics getAnalytics();

    /**
     * Return total number of emails sent
     * @return
     */
    int getEmailSentCount();
}
