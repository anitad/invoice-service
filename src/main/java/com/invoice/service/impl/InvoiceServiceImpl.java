package com.invoice.service.impl;

import com.intuit.invoice.entities.*;
import com.intuit.invoice.repository.*;
import com.invoice.entities.*;
import com.invoice.repository.*;
import com.intuit.invoice.rest.models.*;
import com.invoice.rest.models.*;
import com.invoice.service.InvoiceService;
import com.invoice.service.exception.ProductInvalidException;
import com.invoice.service.exception.InvoiceNotFoundException;
import com.invoice.util.TransformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service("invoiceService")
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransformUtil transformUtil;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Override
    @Transactional
    public Invoices getAllInvoices() {
        logger.info("Getting all invoices..");
        List<InvoiceEntity> invoiceEntities = invoiceRepository.getAll();
        Invoices invoices = new Invoices();
        invoices.setInvoices(transformUtil.convertToRestModelInvoiceList(invoiceEntities));
        return invoices;
    }

    @Override
    @Transactional
    public Invoice getInvoiceById(long invoiceId) {
        logger.info("Getting invoice by id, invoiceId = {}",invoiceId);
        InvoiceEntity invoiceEntity = invoiceRepository.findById(invoiceId);
        if(invoiceEntity == null){
            throw new InvoiceNotFoundException("Invoice does not exist, invoiceId = " + invoiceId);
        }
        Invoice invoice = transformUtil.convertToRestModelInvoice(invoiceEntity);
        return invoice;
    }

    @Override
    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        logger.info("Creating invoice..");
        validateProduct(invoice.getLineItems());
        InvoiceEntity invoiceEntity = createInvoiceEntity(invoice);
        invoice = transformUtil.convertToRestModelInvoice(invoiceEntity);
        return invoice;
    }

    private void validateProduct(List<LineItem> lineItems) {
        for (LineItem lineItem : lineItems) {
            ProductEntity productEntity = productRepository.findByName(lineItem.getProduct().getName());
            if (productEntity == null) {
                throw new ProductInvalidException("Invalid product selection.");
            }
        }
    }

    private CustomerEntity createCustomerEntity(Customer customer){
        logger.info("Creating customer entity..");
        //Check if customer already exists
        CustomerEntity customerEntity = customerRepository.findByEmail(customer.getEmailId());
        if (customerEntity == null) {
            customerEntity = transformUtil.convertToCustomerEntity(customer);
            customerEntity = customerRepository.save(customerEntity);
        }
        //check if customer addresses is already there in the database.
        List<AddressEntity> addressEntities = transformUtil.convertToAddressEntityList(customer.getAddresses(),customerEntity);
        // assuming only one address provided in the input.
        AddressEntity addressEntity = addressRepository.findAddress(addressEntities.get(0));
        // if address not found, save address in the db.
        if (addressEntity == null) {
            addressEntities = addressRepository.save(addressEntities);
            //set the address in customer entity.
            customerEntity.setAddresses(addressEntities);
        }
        return customerEntity;
    }

    private InvoiceEntity createInvoiceEntity(Invoice invoice) {
        logger.info("Creating invoice entity..");
        // create customer
        CustomerEntity customerEntity = createCustomerEntity(invoice.getCustomer());
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setCreationTime(new Date());
        invoiceEntity.setCustomer(customerEntity);
        invoiceEntity = invoiceRepository.save(invoiceEntity);
        //create line item entities
        List<LineItemEntity> lineItemEntities = createLineItemEntities(invoice.getLineItems(), invoiceEntity);
        //set line items on invoice entity.
        invoiceEntity.setLineItems(lineItemEntities);
        double totalAmount = 0;
        //calculate total invoice amount.
        for(LineItemEntity lineItemEntity : lineItemEntities) {
            totalAmount = totalAmount + lineItemEntity.getAmount();
        }
        //set total invoice amount
        invoiceEntity.setTotalAmount(totalAmount);
        invoiceEntity = invoiceRepository.save(invoiceEntity);
        logger.info("Created invoice entity, invoiceId = {}", invoiceEntity.getId());
        return invoiceEntity;
    }

    private List<LineItemEntity> createLineItemEntities(List<LineItem> lineItems, InvoiceEntity invoiceEntity){
        logger.info("Creating line items..");
        //Get all the products from DB.
        List<ProductEntity> productEntities = getProductEntityList();
        List<LineItemEntity> lineItemEntities = transformUtil.convertToLineItemEntityList(lineItems, productEntities, invoiceEntity);
        lineItemEntities = lineItemRepository.save(lineItemEntities);
        logger.info("Created line item entities for invoice, invoiceId = {}",invoiceEntity.getId());
        return lineItemEntities;
    }

    private List<ProductEntity> getProductEntityList(){
        List<ProductEntity> productEntities = productRepository.getAll();
        return productEntities;
    }

    @Override
    public Invoices getAllInvoices(PageInfo pageInfo) {
        //long totalRecords = invoiceRepository.count();
        //pageInfo.setTotalRecords(totalRecords);
        List<InvoiceEntity> invoiceEntities = invoiceRepository.getAll(pageInfo.getOffset(), pageInfo.getLimit());
        Invoices invoices = new Invoices();
        invoices.setInvoices(transformUtil.convertToRestModelInvoiceList(invoiceEntities));
        invoices.setPageInfo(pageInfo);
        return invoices;
    }

}
