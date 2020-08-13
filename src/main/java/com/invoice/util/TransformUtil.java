package com.invoice.util;

import com.intuit.invoice.entities.*;
import com.intuit.invoice.rest.models.*;
import com.invoice.entities.*;
import com.invoice.rest.models.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TransformUtil {

    public Invoice convertToRestModelInvoice(InvoiceEntity invoiceEntity) {
        Customer customer = convertToRestModelCustomer(invoiceEntity.getCustomer());
        List<LineItem> lineItems = convertToRestModelLineItemList(invoiceEntity.getLineItems());
        return new Invoice(invoiceEntity.getId(), invoiceEntity.getCreationTime(), customer, lineItems,invoiceEntity.getTotalAmount() );
    }

    public List<Invoice> convertToRestModelInvoiceList(List<InvoiceEntity> invoiceEntities) {
        List<Invoice> invoices = new ArrayList<>();
        for (InvoiceEntity invoiceEntity : invoiceEntities) {
            Invoice invoice = convertToRestModelInvoice(invoiceEntity);
            invoices.add(invoice);
        }
        return invoices;
    }

    public Customer convertToRestModelCustomer(CustomerEntity customerEntity) {
        Customer customer = new Customer(customerEntity.getId(), customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getEmailId());
        List<Address> addressList = convertToRestModelAddressList(customerEntity.getAddresses());
        customer.setAddresses(addressList);
        return customer;
    }

    public Address convertToRestModelAddress(AddressEntity addressEntity) {
        Address address = new Address(addressEntity.getStreet(), addressEntity.getCity(), addressEntity.getState(),
                addressEntity.getCountry(),
                addressEntity.getZipCode());
        return address;
    }

    public List<Address> convertToRestModelAddressList(List<AddressEntity> addressEntityList) {
        List<Address> addresses = new ArrayList<>();
        for(AddressEntity addressEntity : addressEntityList) {
            addresses.add(convertToRestModelAddress(addressEntity));
        }
        return addresses;
    }

    public CustomerEntity convertToCustomerEntity(Customer customer) {
        return new CustomerEntity(customer.getFirstName(), customer.getLastName(), customer.getEmailId());
    }

    public LineItem convertToRestModelLineItem(LineItemEntity lineItemEntity) {
        Product product = convertToRestModelProduct(lineItemEntity.getProduct());
        return new LineItem(lineItemEntity.getId(), product, lineItemEntity.getQuantity(), lineItemEntity.getAmount());
    }

    public Product convertToRestModelProduct(ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getPrice());

    }

    public AddressEntity convertToAddressEntity(Address address, CustomerEntity customerEntity) {
        AddressEntity addressEntity = new AddressEntity(address.getStreet(), address.getCity(), address.getState(),
                address.getCountry(),
                address.getZipCode());
        addressEntity.setCustomer(customerEntity);
        return addressEntity;
    }

    public List<LineItem> convertToRestModelLineItemList(List<LineItemEntity> lineItemEntityList) {
        List<LineItem> lineItemList = new ArrayList<>();
        for (LineItemEntity lineItemEntity : lineItemEntityList) {
            LineItem lineItem = convertToRestModelLineItem(lineItemEntity);
            lineItemList.add(lineItem);
        }
        return lineItemList;
    }

    public List<LineItemEntity> convertToLineItemEntityList(List<LineItem> lineItems,
                                                            List<ProductEntity> productEntities,
                                                            InvoiceEntity invoiceEntity) {
        List<LineItemEntity> lineItemEntityList = new ArrayList<>();
        HashMap<String, ProductEntity> productEntityHashMap = getProductEntityMap(productEntities);
        for (LineItem lineItem : lineItems) {
            LineItemEntity lineItemEntity = new LineItemEntity();
            ProductEntity productEntity = productEntityHashMap.get(lineItem.getProduct().getName());
            lineItemEntity.setProduct(productEntity);
            lineItemEntity.setInvoice(invoiceEntity);
            lineItemEntity.setQuantity(lineItem.getQuantity());
            lineItemEntity.setAmount(productEntity.getPrice() * lineItem.getQuantity());
            lineItemEntityList.add(lineItemEntity);
        }
        return lineItemEntityList;
    }

    public List<AddressEntity> convertToAddressEntityList(List<Address> addresses, CustomerEntity customerEntity) {
        List<AddressEntity> addressEntities = new ArrayList<>();
        for(Address address : addresses) {
            AddressEntity addressEntity = convertToAddressEntity(address, customerEntity);
            addressEntities.add(addressEntity);
        }
        return addressEntities;
    }

    /*
    Returns a map of product entities by product name.
     */
    public HashMap<String, ProductEntity> getProductEntityMap(List<ProductEntity> productEntities) {
        HashMap<String, ProductEntity> productEntityHashMap = new HashMap<>();
        for (ProductEntity productEntity : productEntities) {
            productEntityHashMap.put(productEntity.getName(), productEntity);
        }
        return productEntityHashMap;

    }

    public List<ProductAnalytics> convertToRestModelProductAnalyticsList(List result) {
        List<ProductAnalytics> analyticsList = new ArrayList<>();
        for (Object o : result) {
            ProductAnalytics analytics = convertToRestModelProductAnalytics(o);
            analyticsList.add(analytics);
        }
        return analyticsList;
    }

    public List<InvoiceAnalytics> convertToRestModelInvoiceAnalyticsList(List result) {
        List<InvoiceAnalytics> analyticsList = new ArrayList<>();
        for (Object o : result) {
            InvoiceAnalytics analytics = convertToRestModelInvoiceAnalytics(o);
            analyticsList.add(analytics);
        }
        return analyticsList;
    }

    public ProductAnalytics convertToRestModelProductAnalytics(Object result) {

        Object[] data = (Object[]) result;
        ProductAnalytics analytics = new ProductAnalytics();
        analytics.setProductId(((ProductEntity) data[0]).getId());
        analytics.setProductName(((ProductEntity) data[0]).getName());
        analytics.setTotalQuantity((Long) data[1]);
        analytics.setTotalAmount((Double) data[2]);
        return analytics;

    }

    public InvoiceAnalytics convertToRestModelInvoiceAnalytics(Object result) {

        Object[] data = (Object[]) result;
        InvoiceAnalytics analytics = new InvoiceAnalytics();
        analytics.setInvoiceId(((InvoiceEntity) data[0]).getId());
        analytics.setTotalAmount((Double) data[1]);
        return analytics;

    }
}
