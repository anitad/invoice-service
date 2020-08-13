package com.invoice.util;

import com.invoice.TestHelper;
import com.invoice.entities.AddressEntity;
import com.invoice.entities.InvoiceEntity;
import com.invoice.entities.LineItemEntity;
import com.invoice.entities.ProductEntity;
import com.intuit.invoice.rest.models.*;
import com.invoice.rest.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransformUtilTest {


    @InjectMocks
    private TransformUtil transformUtil;

    private List<InvoiceEntity> invoiceEntities = TestHelper.getMockedInvoiceEntityList();
    private List<Invoice> invoices = TestHelper.getMockedInvoiceList();
    private InvoiceEntity invoiceEntity = TestHelper.getMockedInvoiceEntity();
    private Invoice invoice = TestHelper.getMockedInvoice();
    private Analytics analytics = TestHelper.getMockedAnalytics();


    @Test
    public void convertToRestModelInvoiceList() {
        List<Invoice> invoiceList = transformUtil.convertToRestModelInvoiceList(invoiceEntities);
        /*
        verify test results
         */
        assertNotNull(invoiceList);
        assertEquals(1,invoiceList.size());
        Invoice invoice = invoiceList.get(0);
        assertEquals(invoiceEntity.getId(),invoice.getId());
        assertNotNull(invoice.getCustomer());
        assertEquals(invoiceEntity.getCustomer().getId(),invoice.getCustomer().getId());
    }


    @Test
    public void convertToRestModelLineItemList() {
        List<LineItem> lineItemList = transformUtil.convertToRestModelLineItemList(invoiceEntity.getLineItems());
        /*
        verify test results
         */
        assertNotNull(lineItemList);
        assertEquals(2,lineItemList.size());
        assertEquals(invoiceEntity.getLineItems().get(0).getId(), lineItemList.get(0).getId());

    }

    @Test
    public void convertToLineItemEntityList() {
        List<ProductEntity> productEntities = new ArrayList<>();
        ProductEntity productEntity = invoiceEntity.getLineItems().get(0).getProduct();
        productEntities.add(productEntity);
        productEntity = invoiceEntity.getLineItems().get(1).getProduct();
        productEntities.add(productEntity);
        List<LineItemEntity> lineItemEntities = transformUtil.convertToLineItemEntityList(invoice.getLineItems(),productEntities,invoiceEntity);
        /*
        verify test results
         */
        assertNotNull(lineItemEntities);
        assertEquals(2,lineItemEntities.size());
//        assertEquals(invoice.getLineItems().get(0).getId(), lineItemEntities.get(0).getId());
    }

    @Test
    public void convertToAddressEntityList() {
        List<AddressEntity> addressEntities = transformUtil.convertToAddressEntityList(invoice.getCustomer().getAddresses(),invoiceEntity.getCustomer());
        /*
        verify test results
         */
        assertNotNull(addressEntities);
        assertEquals(1,addressEntities.size());
        assertEquals(invoice.getCustomer().getAddresses().get(0).getZipCode(), addressEntities.get(0).getZipCode());
    }

    @Test
    public void getProductEntityMap() {
    }

    @Test
    public void convertToRestModelProductAnalyticsList() {
        List result = TestHelper.getProductAnalyticsResult();
        List<ProductAnalytics> productAnalyticsList = transformUtil.convertToRestModelProductAnalyticsList(result);
        /*
        verify test results
         */
        assertNotNull(productAnalyticsList);
        assertEquals(1, productAnalyticsList.size());
        assertEquals(6l, productAnalyticsList.get(0).getTotalQuantity());
    }

    @Test
    public void convertToRestModelInvoiceAnalyticsList() {
        List result = TestHelper.getInvoiceAnalyticsResult();
        List<InvoiceAnalytics> invoiceAnalyticsList = transformUtil.convertToRestModelInvoiceAnalyticsList(result);
        /*
        verify test results
         */
        assertNotNull(invoiceAnalyticsList);
        assertEquals(2, invoiceAnalyticsList.size());
        assertEquals(650.00, invoiceAnalyticsList.get(0).getTotalAmount(),0);
    }
}