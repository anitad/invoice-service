package com.invoice.service;

import com.invoice.rest.models.Customer;

/**
 * Customer service interface with set of public
 * apis for performing customer operations.
 */
public interface CustomerService {

     /**
      * Creates a new customer
      * @param customer
      * @return new Customer object.
      */
     Customer createCustomer(Customer customer);
}
