package com.invoice.repository;

import com.invoice.entities.AddressEntity;

/**
 *  Address repository with apis for
 *  performing database operations on
 *  Address entity.
 */
public interface AddressRepository extends GenericRepository<AddressEntity> {

    /**
     * Finds and returns an address.
     * @param addressEntity
     * @return
     */
    AddressEntity findAddress(AddressEntity addressEntity);
}
