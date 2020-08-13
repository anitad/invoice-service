package com.invoice.repository.impl;

import com.invoice.entities.InvoiceEntity;
import com.invoice.repository.InvoiceRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("invoiceRepository")
@Repository
public class InvoiceRepositoryImpl extends AbstractGenericRepositoryImpl<InvoiceEntity> implements InvoiceRepository {

    @Override
    public Class<InvoiceEntity> getEntityClass() {
        return InvoiceEntity.class;
    }
}
