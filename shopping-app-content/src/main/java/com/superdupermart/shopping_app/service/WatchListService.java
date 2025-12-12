package com.superdupermart.shopping_app.service;


import com.superdupermart.shopping_app.dao.ProductDao;
import com.superdupermart.shopping_app.dao.WatchListDao;
import com.superdupermart.shopping_app.dto.ProductUserView;
import com.superdupermart.shopping_app.entity.Product;
import com.superdupermart.shopping_app.entity.User;
import com.superdupermart.shopping_app.entity.WatchList;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchListService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private WatchListDao watchListDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserService userService;

    @Transactional
    public void addToWatchList(Long productId) {
        Session session = sessionFactory.getCurrentSession();
        User user = userService.getCurrentAuthenticatedUser();
        Product product = session.get(Product.class, productId);
        if (product == null) throw new RuntimeException("Product not found");

        WatchList existing = watchListDao.findByUserAndProduct(user, product);
        if (existing == null) {
            WatchList entry = new WatchList();
            entry.setUser(user);
            entry.setProduct(product);
            session.persist(entry);
        }
    }

    @Transactional
    public void removeFromWatchList(Long productId) {
        Session session = sessionFactory.getCurrentSession();
        User user = userService.getCurrentAuthenticatedUser();
        Product product = session.get(Product.class, productId);
        if (product == null) throw new RuntimeException("Product not found");

        WatchList watchlist = watchListDao.findByUserAndProduct(user, product);
        if (watchlist == null) {
            throw new EntityNotFoundException("Product not found in watchlist.");
        }
        if (watchlist != null) {
            session.remove(watchlist);
        }
    }

    @Transactional
    public List<ProductUserView> getInStockWatchListItems() {
        User user = userService.getCurrentAuthenticatedUser();
        List<Product> products = watchListDao.findInStockWatchlistProductsByUser(user.getUserId());
        return products.stream()
                .map(p -> new ProductUserView(p.getProductId(), p.getName(), p.getRetailPrice()))
                .collect(Collectors.toList());
    }
}

