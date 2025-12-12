package com.superdupermart.shopping_app.dao;


import com.superdupermart.shopping_app.entity.Product;
import com.superdupermart.shopping_app.entity.User;
import com.superdupermart.shopping_app.entity.WatchList;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.*;
import java.util.List;

@Repository
public class WatchListDao extends AbstractHibernateDao<WatchList> {

    public WatchListDao() {
        setClazz(WatchList.class);
    }

    public List<Product> findInStockWatchlistProductsByUser(Long userId) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);

        Root<WatchList> root = query.from(WatchList.class);
        Join<WatchList, Product> productJoin = root.join("product");

        query.select(productJoin)
                .where(cb.and(
                        cb.equal(root.get("user").get("userId"), userId),
                        cb.gt(productJoin.get("quantity"), 0)
                ))
                .distinct(true);

        return session.createQuery(query).getResultList();
    }

    public WatchList findByUserAndProduct(User user, Product product) {
        Session session = getCurrentSession();
        return session.createQuery(
                        "FROM WatchList w WHERE w.user = :user AND w.product = :product", WatchList.class)
                .setParameter("user", user)
                .setParameter("product", product)
                .uniqueResult();
    }
}
