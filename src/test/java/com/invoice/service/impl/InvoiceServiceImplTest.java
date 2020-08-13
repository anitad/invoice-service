package com.invoice.service.impl;

import com.invoice.TestHelper;

import com.invoice.entities.*;
import com.invoice.repository.*;
import com.invoice.rest.models.Customer;
import com.invoice.rest.models.Invoice;
import com.invoice.rest.models.Product;
import com.invoice.service.exception.InvoiceNotFoundException;
import com.invoice.service.exception.ProductInvalidException;
import com.invoice.util.TransformUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransformUtil transformUtil;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private LineItemRepository lineItemRepository;


    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<InvoiceEntity> invoiceEntities = TestHelper.getMockedInvoiceEntityList();
    private List<Invoice> invoices = TestHelper.getMockedInvoiceList();
    private InvoiceEntity invoiceEntity = TestHelper.getMockedInvoiceEntity();
    private Invoice invoice = TestHelper.getMockedInvoice();

    @Test
    public void getAllInvoices() {
        /*
        mocked calls
         */
        when(invoiceRepository.getAll()).thenReturn(invoiceEntities);
        when(transformUtil.convertToRestModelInvoiceList(invoiceEntities)).thenReturn(invoices);

        /*
        call actual method to be tested
         */
        List<Invoice> invoiceList = invoiceService.getAllInvoices().getInvoices();
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
    public void getInvoiceById() {

        /*
        mocked calls
        */
        when(invoiceRepository.findById(invoice.getId())).thenReturn(invoiceEntity);
        when(transformUtil.convertToRestModelInvoice(invoiceEntity)).thenReturn(invoice);
        /*
        call actual method to be tested
         */
        Invoice invoice1 = invoiceService.getInvoiceById(invoice.getId());
        /*
        verify test results
         */
        assertNotNull(invoice1);
        assertEquals(invoiceEntity.getId(),invoice1.getId());
        assertNotNull(invoice1.getCustomer());
        assertEquals(invoiceEntity.getCustomer().getId(),invoice1.getCustomer().getId());
    }

    @Test
    public void createInvoice() {
        /*
        mocked calls
        */
        Product product = invoice.getLineItems().get(0).getProduct();
        String productName = product.getName();
        ProductEntity productEntity = new ProductEntity();
        when(productRepository.findByName(any())).thenReturn(productEntity);

        Customer customer = invoice.getCustomer();
        String emailId = customer.getEmailId();
        CustomerEntity customerEntity = new CustomerEntity();
        when(customerRepository.findByEmail(emailId)).thenReturn(null);
        when(transformUtil.convertToCustomerEntity(customer)).thenReturn(customerEntity);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);

        List<AddressEntity> addressEntities = new ArrayList<>();
        when(transformUtil.convertToAddressEntityList(customer.getAddresses(),customerEntity)).thenReturn(addressEntities);
        AddressEntity addressEntity = new AddressEntity();
        addressEntities.add(addressEntity);
        when(addressRepository.findAddress(addressEntities.get(0))).thenReturn(null);
        when(addressRepository.save(addressEntities)).thenReturn(addressEntities);
        when(invoiceRepository.save(any(InvoiceEntity.class))).thenReturn(invoiceEntity);

        List<ProductEntity> productEntities = new ArrayList<>();
        when(productRepository.getAll()).thenReturn(productEntities);
        List<LineItemEntity> lineItemEntities = new ArrayList<>();
        when(transformUtil.convertToLineItemEntityList(invoice.getLineItems(),productEntities,invoiceEntity)).thenReturn(lineItemEntities);
        when(lineItemRepository.save(lineItemEntities)).thenReturn(lineItemEntities);
        when(transformUtil.convertToRestModelInvoice(invoiceEntity)).thenReturn(invoice);

        /*
        call actual method to be tested
         */
        Invoice invoice1 = invoiceService.createInvoice(invoice);

        /*
        verify test results
         */
        assertNotNull(invoice1);
        assertEquals(invoiceEntity.getId(),invoice1.getId());
        assertNotNull(invoice1.getCustomer());
        assertEquals(invoiceEntity.getCustomer().getId(),invoice1.getCustomer().getId());
    }

    @Test
    public void createInvoiceThrowsProductInvalidException(){
        when(productRepository.findByName(any())).thenReturn(null);
        /*
        Expected Exception
         */
        expectedException.expect(ProductInvalidException.class);
        expectedException.expectMessage("Invalid product selection.");
        /*
        call actual method to be tested
         */
        Invoice invoice1 = invoiceService.createInvoice(invoice);
    }

    @Test
    public void getInvoiceByIdThrowsInvoiceNotFoundException(){
        when(invoiceRepository.findById(invoice.getId())).thenReturn(null);
        /*
        Expected Exception
         */
        expectedException.expect(InvoiceNotFoundException.class);
        expectedException.expectMessage("Invoice does not exist, invoiceId = " + invoice.getId());
        /*
        call actual method to be tested
         */
        Invoice invoice1 = invoiceService.getInvoiceById(invoice.getId());

    }
}