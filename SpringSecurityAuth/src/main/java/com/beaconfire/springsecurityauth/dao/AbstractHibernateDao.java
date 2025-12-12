package com.beaconfire.springsecurityauth.dao;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateDao<T extends Serializable> {

    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> clazz;

    protected void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findById(final Long id) {
        return getCurrentSession().get(clazz, id);
    }

    public List<T> getAll() {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).getResultList();
    }

    public void save(final T entity) {
        getCurrentSession().save(entity);
    }

    public void update(final T entity) {
        getCurrentSession().update(entity);
    }

    public void delete(final T entity) {
        getCurrentSession().delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
