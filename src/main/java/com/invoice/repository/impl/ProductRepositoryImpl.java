package com.invoice.repository.impl;

import com.invoice.entities.ProductEntity;
import com.invoice.repository.ProductRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("productRepository")
@Repository
public class ProductRepositoryImpl extends AbstractGenericRepositoryImpl<ProductEntity> implements ProductRepository {

    @Override
    public Class<ProductEntity> getEntityClass() {
        return ProductEntity.class;
    }

    @Override
    public ProductEntity findByName(String name) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("name",name));
        return (ProductEntity) criteria.uniqueResult();
    }
}
