package com.invoice.repository.impl;

import com.invoice.repository.GenericRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractGenericRepositoryImpl<T extends Serializable> implements GenericRepository<T> {

    @Autowired
    SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected abstract Class<T> getEntityClass();

    @Override
    public List<T> getAll() {
        return getSession().createQuery("from " + getEntityClass().getName()).list();
    }

    @Override
    public T save(T entity) {
         getSession().saveOrUpdate(entity);
         return entity;
    }

    @Override
    public List<T> save(List<T> entities) {
        for(T entity : entities) {
            save(entity);
        }
        return entities;
    }

    @Override
    public T findById(long id) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("id",id));
        return (T) criteria.uniqueResult();
    }

    @Override
    public List<T> getAll(int offset, int limit) {
        Query query = getSession().createQuery("from " + getEntityClass().getName());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }
}
