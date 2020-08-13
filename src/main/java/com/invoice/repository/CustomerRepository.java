package com.invoice.repository;

import com.invoice.entities.CustomerEntity;

/**
 * Customer repository.
 */
public interface CustomerRepository extends GenericRepository<CustomerEntity> {

    /**
     * Finds and returns a customer by
     * email.
     * @param email
     * @return
     */
    CustomerEntity findByEmail(String email);
}
