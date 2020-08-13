package com.invoice.repository.impl;

import com.invoice.entities.CustomerEntity;
import com.invoice.repository.CustomerRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("customerRepository")
@Repository
public class CustomerRepositoryImpl extends AbstractGenericRepositoryImpl<CustomerEntity> implements CustomerRepository {
    @Override
    public Class<CustomerEntity> getEntityClass() {
        return CustomerEntity.class;
    }

    @Override
    public CustomerEntity findByEmail(String email) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("emailId",email));
        return (CustomerEntity) criteria.uniqueResult();
    }
}
