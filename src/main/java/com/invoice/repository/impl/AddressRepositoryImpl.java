package com.invoice.repository.impl;

import com.invoice.entities.AddressEntity;
import com.invoice.repository.AddressRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("addressRepository")
@Repository
public class AddressRepositoryImpl extends AbstractGenericRepositoryImpl<AddressEntity> implements AddressRepository {

    @Override
    public Class<AddressEntity> getEntityClass() {
        return AddressEntity.class;
    }

    @Override
    public AddressEntity findAddress(AddressEntity addressEntity) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("street",addressEntity.getStreet()));
        criteria.add(Restrictions.eq("city",addressEntity.getCity()));
        criteria.add(Restrictions.eq("state",addressEntity.getState()));
        criteria.add(Restrictions.eq("customer",addressEntity.getCustomer()));
        return (AddressEntity) criteria.uniqueResult();
    }
}
