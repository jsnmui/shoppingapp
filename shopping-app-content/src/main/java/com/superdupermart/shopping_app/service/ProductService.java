package com.superdupermart.shopping_app.service;

import com.superdupermart.shopping_app.dao.ProductDao;
import com.superdupermart.shopping_app.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<Product> getAllInStockProducts() {
        return productDao.findAllInStock();
    }


    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productDao.findAllProducts();
    }


    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productDao.findProductById(id);
    }

    @Transactional
    public void updateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(product);
    }

    @Transactional
    public void saveProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(product);
    }

}