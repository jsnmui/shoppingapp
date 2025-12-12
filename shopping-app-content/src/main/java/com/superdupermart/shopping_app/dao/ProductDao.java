package com.superdupermart.shopping_app.dao;

import com.superdupermart.shopping_app.entity.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductDao extends AbstractHibernateDao<Product> {

    public ProductDao() {
        setClazz(Product.class);
    }

    public List<Product> findAllInStock() {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root).where(cb.gt(root.get("quantity"), 0));

        return session.createQuery(query).getResultList();
    }

    // For sellers/admin: All products, including out-of-stock
    public List<Product> findAllProducts() {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root); // No filtering

        return session.createQuery(query).getResultList();
    }


    public Product findProductById(Long id) {
        return findById(id);
    }



}


