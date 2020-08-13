package com.invoice.service.impl;

import com.invoice.TestHelper;
import com.invoice.entities.EmailSentEntity;
import com.invoice.entities.ProductEntity;
import com.invoice.repository.EmailSentRepository;
import com.invoice.repository.LineItemRepository;
import com.invoice.repository.ProductRepository;
import com.invoice.rest.models.Analytics;
import com.invoice.rest.models.InvoiceAnalytics;
import com.invoice.rest.models.ProductAnalytics;
import com.invoice.util.TransformUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class AnalyticsServiceImplTest {

    @Mock
    private LineItemRepository lineItemRepository;

    @Mock
    private TransformUtil transformUtil;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AnalyticsServiceImpl analyticsService;

    @Mock
    private EmailSentRepository emailSentRepository;

    private List productAnalyticsResult = TestHelper.getProductAnalyticsResult();
    private  List<ProductAnalytics> productAnalyticsListMock = TestHelper.getMockedAnalytics().getProductAnalytics();
    private List invoiceAnalyticsResult = TestHelper.getInvoiceAnalyticsResult();
    private List<InvoiceAnalytics> invoiceAnalyticsListMock = TestHelper.getMockedAnalytics().getInvoiceAnalytics();

    @Test
    public void getProductAnalytics() {
        when(lineItemRepository.getTotalAmtForInvoices()).thenReturn(productAnalyticsResult);
        when(transformUtil.convertToRestModelProductAnalyticsList(any())).thenReturn(productAnalyticsListMock);
        List<ProductAnalytics> productAnalyticsList = analyticsService.getProductAnalytics();

         /*
        verify test results
         */
        assertNotNull(productAnalyticsList);
        assertEquals(productAnalyticsListMock.size(), productAnalyticsList.size());
        assertEquals(productAnalyticsListMock.get(0).getTotalQuantity(), productAnalyticsList.get(0).getTotalQuantity());
    }

    @Test
    public void getProductAnalyticsForOne() {
        ProductEntity productEntity = new ProductEntity();
        when(productRepository.findById(productAnalyticsListMock.get(0).getProductId())).thenReturn(productEntity);
        when(lineItemRepository.getTotalAmtAndQtyByProductId(productEntity)).thenReturn(new Object());
        when(transformUtil.convertToRestModelProductAnalytics(any())).thenReturn(TestHelper.getMockedAnalytics().getProductAnalytics().get(0));
        ProductAnalytics productAnalytics = analyticsService.getProductAnalyticsForOne(productAnalyticsListMock.get(0).getProductId());
        /*
        verify test results
         */
        assertNotNull(productAnalytics);
        assertEquals(productAnalytics.getTotalQuantity(), productAnalytics.getTotalQuantity());
    }

    @Test
    public void getInvoiceAnalytics() {
       when(lineItemRepository.getTotalAmtForInvoices()).thenReturn(invoiceAnalyticsResult);
       when(transformUtil.convertToRestModelInvoiceAnalyticsList(any())).thenReturn(invoiceAnalyticsListMock);
       List<InvoiceAnalytics> invoiceAnalyticsList =  analyticsService.getInvoiceAnalytics();
        /*
        verify test results
         */
        assertNotNull(invoiceAnalyticsList);
        assertEquals(invoiceAnalyticsListMock.size(),invoiceAnalyticsList.size());
        assertEquals(invoiceAnalyticsListMock.get(0).getInvoiceId(),invoiceAnalyticsList.get(0).getInvoiceId());

    }

    @Test
    public void getAnalytics() {
        when(lineItemRepository.getTotalAmtForInvoices()).thenReturn(productAnalyticsResult);
        when(transformUtil.convertToRestModelProductAnalyticsList(any())).thenReturn(productAnalyticsListMock);

        when(lineItemRepository.getTotalAmtForInvoices()).thenReturn(invoiceAnalyticsResult);
        when(transformUtil.convertToRestModelInvoiceAnalyticsList(any())).thenReturn(invoiceAnalyticsListMock);
        List<EmailSentEntity> emailSentEntities = new ArrayList<>();
        when(emailSentRepository.getAll()).thenReturn(emailSentEntities);

        Analytics analytics = analyticsService.getAnalytics();

        /*
        verify test results
         */
        assertNotNull(analytics);
        assertNotNull(analytics.getInvoiceAnalytics());
        assertNotNull(analytics.getProductAnalytics());
        assertEquals(invoiceAnalyticsListMock.size(),analytics.getInvoiceAnalytics().size());
        assertEquals(productAnalyticsListMock.size(),analytics.getProductAnalytics().size());
        assertEquals(invoiceAnalyticsListMock.get(0).getInvoiceId(),analytics.getInvoiceAnalytics().get(0).getInvoiceId());
        assertEquals(productAnalyticsListMock.get(0).getTotalQuantity(), analytics.getProductAnalytics().get(0).getTotalQuantity());

    }
}