package com.invoice.repository.impl;

import com.intuit.invoice.entities.*;
import com.invoice.repository.LineItemRepository;
import com.invoice.entities.LineItemEntity;
import com.invoice.entities.ProductEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component("lineItemRepository")
@Repository
public class LineItemRepositoryImpl extends AbstractGenericRepositoryImpl<LineItemEntity> implements LineItemRepository {
    @Override
    public Class<LineItemEntity> getEntityClass() {
        return LineItemEntity.class;
    }

    @Override
    public List getTotalAmtAndQtyForProducts() {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        List result = criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("product"),"product")
                .add(Projections.sum("quantity"))
                .add(Projections.sum("amount")))
                .list();
        return result;
    }

    @Override
    public List getTotalAmtForInvoices() {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        List result = criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("invoice"))
                .add(Projections.sum("amount")))
                .list();
        return result;
    }

    @Override
    public Object getTotalAmtAndQtyByProductId(ProductEntity productEntity) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("product",productEntity));
        Object result = criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("product"),"product")
                .add(Projections.sum("quantity"))
                .add(Projections.sum("amount")))
                .uniqueResult();
        return result;
    }
}
