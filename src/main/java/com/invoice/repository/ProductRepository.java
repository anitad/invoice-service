package com.invoice.repository;

import com.invoice.entities.ProductEntity;

/**
 * Product repository
 */
public interface ProductRepository extends GenericRepository<ProductEntity> {

    /**
     * Finds and returns a product by name.
     * @param name
     * @return
     */
    ProductEntity findByName(String name);
}
