package com.invoice.repository;

import com.invoice.entities.LineItemEntity;
import com.invoice.entities.ProductEntity;

import java.util.List;

/**
 * Line item repository.
 */
public interface LineItemRepository extends GenericRepository<LineItemEntity> {

    /**
     * Returns the total amount and quantity for
     * products.
     * @return
     */
    List getTotalAmtAndQtyForProducts();

    /**
     * Returns the total amount for invoices.
     * @return
     */
    List getTotalAmtForInvoices();

    /**
     * Returns the total amount and quantity
     * for a given product.
     * @param productEntity
     * @return
     */
    Object getTotalAmtAndQtyByProductId(ProductEntity productEntity);

}
