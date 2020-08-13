package com.invoice.service.impl;

import com.invoice.entities.EmailSentEntity;
import com.invoice.entities.ProductEntity;
import com.invoice.repository.EmailSentRepository;
import com.invoice.repository.LineItemRepository;
import com.invoice.repository.ProductRepository;
import com.invoice.rest.models.Analytics;
import com.invoice.rest.models.InvoiceAnalytics;
import com.invoice.rest.models.ProductAnalytics;
import com.invoice.service.AnalyticsService;
import com.invoice.service.exception.ProductNotFoundException;
import com.invoice.util.TransformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("analyticsService")
@Transactional
public class AnalyticsServiceImpl implements AnalyticsService {

    private Logger logger = LoggerFactory.getLogger(AnalyticsServiceImpl.class);

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransformUtil transformUtil;

    @Autowired
    private EmailSentRepository emailSentRepository;

    @Override
    public List<ProductAnalytics> getProductAnalytics() {
        logger.info("Getting product analytics..");
        List result = lineItemRepository.getTotalAmtAndQtyForProducts();
        List<ProductAnalytics> productAnalyticsList = transformUtil.convertToRestModelProductAnalyticsList(result);
        return productAnalyticsList;
    }

    @Override
    public ProductAnalytics getProductAnalyticsForOne(long productId) {
        logger.info("Getting product analytics for product, productId = {}", productId);
        ProductEntity productEntity = productRepository.findById(productId);
        if (productEntity == null) {
            logger.debug("ProductEntity not found for productId = {}", productId);
            throw new ProductNotFoundException("Product with given id not found, productId = " + productId);
        }
        Object productAnalyticsDto = lineItemRepository.getTotalAmtAndQtyByProductId(productEntity);
        return transformUtil.convertToRestModelProductAnalytics(productAnalyticsDto);
    }

    @Override
    public List<InvoiceAnalytics> getInvoiceAnalytics() {
        logger.info("Getting invoice analytics..");
        List result = lineItemRepository.getTotalAmtForInvoices();
        List<InvoiceAnalytics> invoiceAnalyticsList = transformUtil.convertToRestModelInvoiceAnalyticsList(result);
        return invoiceAnalyticsList;
    }

    @Override
    public Analytics getAnalytics() {
        logger.info("Getting all analytics..");
        Analytics analytics = new Analytics();
        analytics.setProductAnalytics(getProductAnalytics());
        analytics.setInvoiceAnalytics(getInvoiceAnalytics());
        analytics.setTotalEmailsSent(getEmailSentCount());
        return analytics;
    }

    @Override
    public int getEmailSentCount() {
        logger.info("Getting email analytics..");
        int count;
        List<EmailSentEntity> emailSentEntities = emailSentRepository.getAll();
        count = emailSentEntities.size();
        return count;
    }

}
