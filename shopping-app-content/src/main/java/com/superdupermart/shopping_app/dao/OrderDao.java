package com.superdupermart.shopping_app.dao;

import com.superdupermart.shopping_app.dto.PopularProductDTO;
import com.superdupermart.shopping_app.dto.FrequentProductDTO;
import com.superdupermart.shopping_app.dto.ProfitProductDTO;
import com.superdupermart.shopping_app.dto.RecentProductDTO;
import com.superdupermart.shopping_app.entity.Order;
import com.superdupermart.shopping_app.entity.Product;
import com.superdupermart.shopping_app.entity.OrderItem;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao extends AbstractHibernateDao<Order> {

        public OrderDao() {
            setClazz(Order.class);
        }

//        public List<Order> findOrdersByUserId(Long userId) {
//            Session session = getCurrentSession();
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Order> query = cb.createQuery(Order.class);
//            Root<Order> root = query.from(Order.class);
//            // Join fetch to load items eagerly
//            root.fetch("items", JoinType.INNER);
//
//            query.select(root)
//                    .where(cb.equal(root.get("user").get("userId"), userId))
//                    .distinct(true);
//
//            return session.createQuery(query).getResultList();
//        }

    public List<Order> findOrdersByUserId(Long userId) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);

        // Join fetch to load items eagerly
        root.fetch("items", JoinType.INNER);

        query.select(root)
                .where(cb.equal(root.get("user").get("userId"), userId))
                .orderBy(cb.desc(root.get("datePlaced"))) // Sort by datePlaced descending
                .distinct(true);

        return session.createQuery(query).getResultList();
    }


    public Order findOrderById(Long orderId) {
        Session session = getCurrentSession();
        System.out.println(session);
        Order order = session.get(Order.class, orderId);
        System.out.println(order);
        if (order == null) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        return order;
    }
//
//    public List<Product> findRecentProductsByUserId(Long userId, int limit) {
//        Session session = getCurrentSession();
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Product> query = cb.createQuery(Product.class);
//        Root<OrderItem> root = query.from(OrderItem.class);
//
//        Join<OrderItem, Order> orderJoin = root.join("order");
//        Join<OrderItem, Product> productJoin = root.join("product");
//
//        query.select(productJoin)
//                .where(
//                        cb.and(
//                                cb.equal(orderJoin.get("user").get("userId"), userId),
//                                cb.notEqual(orderJoin.get("orderStatus"), "Canceled")
//                        )
//                )
//                .orderBy(
//                        cb.desc(orderJoin.get("datePlaced")),
//                        cb.desc(root.get("itemId"))
//                );
//
//
//        return session.createQuery(query)
//                .setMaxResults(limit)
//                .getResultList();
//    }

    public List<RecentProductDTO> findRecentProductsByUserId(Long userId, int limit) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<RecentProductDTO> query = cb.createQuery(RecentProductDTO.class);

        Root<OrderItem> root = query.from(OrderItem.class);
        Join<OrderItem, Order> orderJoin = root.join("order");
        Join<OrderItem, Product> productJoin = root.join("product");

        query.select(cb.construct(
                        RecentProductDTO.class,
                        productJoin.get("productId").alias("productId"),
                        productJoin.get("name").alias("productName"),
                        orderJoin.get("datePlaced").alias("datePurchased")

                ))
                .where(cb.and(
                        cb.equal(orderJoin.get("user").get("userId"), userId),
//                        cb.equal(orderJoin.get("orderStatus"), "Completed")
                        cb.notEqual(orderJoin.get("orderStatus"), "Canceled")
                ))
                .orderBy(cb.desc(orderJoin.get("datePlaced")));

        return session.createQuery(query)
                .setMaxResults(limit)
                .getResultList();
    }


//    public List<Product> findMostFrequentProductsByUserId(Long userId, int limit) {
//        Session session = getCurrentSession();
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
//
//        Root<OrderItem> itemRoot = query.from(OrderItem.class);
//        Join<OrderItem, Order> orderJoin = itemRoot.join("order");
//        Join<OrderItem, Product> productJoin = itemRoot.join("product");
//
//        query.multiselect(productJoin, cb.count(itemRoot.get("itemId")))
//                .where(cb.and(
//                        cb.equal(orderJoin.get("user").get("userId"), userId),
//                        cb.notEqual(orderJoin.get("orderStatus"), "Canceled")
//                ))
//                .groupBy(productJoin)
//                .orderBy(cb.desc(cb.count(itemRoot.get("itemId"))), cb.asc(productJoin.get("productId")));
//
//        return session.createQuery(query)
//                .setMaxResults(limit)
//                .getResultList()
//                .stream()
//                .map(result -> (Product) result[0])
//                .toList();
//    }



    public List<FrequentProductDTO> findMostFrequentProductsByUserId(Long userId, int limit) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<FrequentProductDTO> query = cb.createQuery(FrequentProductDTO.class);

        Root<OrderItem> itemRoot = query.from(OrderItem.class);
        Join<OrderItem, Order> orderJoin = itemRoot.join("order");
        Join<OrderItem, Product> productJoin = itemRoot.join("product");

        query.select(cb.construct(
                        FrequentProductDTO.class,
                        productJoin.get("productId"),
                        productJoin.get("name"),
                        cb.sumAsLong(itemRoot.get("quantity"))
                ))
                .where(cb.and(
                        cb.equal(orderJoin.get("user").get("userId"), userId),
                        cb.notEqual(orderJoin.get("orderStatus"), "Canceled")
                ))
                .groupBy(productJoin.get("productId"), productJoin.get("name"))
                .orderBy(
                        cb.desc(cb.sum(itemRoot.get("quantity"))),
                        cb.asc(productJoin.get("productId"))
                );

        return session.createQuery(query)
                .setMaxResults(limit)
                .getResultList();
    }


    public List<Order> findAllOrdersWithUsers() {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);


        root.fetch("user", JoinType.LEFT);
        query.select(root).distinct(true);
        return session.createQuery(query).getResultList();
    }

    public List<ProfitProductDTO> findTopProfitableProducts(int count) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProfitProductDTO> query = cb.createQuery(ProfitProductDTO.class);

        Root<OrderItem> root = query.from(OrderItem.class);
        Join<OrderItem, Product> productJoin = root.join("product");
        Join<OrderItem, Order> orderJoin = root.join("order");

        // Calculate profit per item: (purchasedPrice - wholesalePrice) * quantity
        Expression<Double> profitExpr = cb.prod(
                cb.diff(root.get("purchasedPrice"), root.get("wholesalePrice")),
                root.get("quantity")
        );

        query.select(cb.construct(
                        ProfitProductDTO.class,
                        productJoin.get("productId"),
                        productJoin.get("name"),
                        cb.sum(profitExpr)
                ))
                .where(orderJoin.get("orderStatus").in("Completed")) // Exclude "Processing" and "Canceled"
                .groupBy(productJoin.get("productId"), productJoin.get("name"))
                .orderBy(cb.desc(cb.sum(profitExpr)));

        return session.createQuery(query)
                .setMaxResults(count)
                .getResultList();
    }


    public List<PopularProductDTO> findMostPopularProducts(int count) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PopularProductDTO> query = cb.createQuery(PopularProductDTO.class);

        Root<OrderItem> root = query.from(OrderItem.class);
        Join<OrderItem, Order> orderJoin = root.join("order");
        Join<OrderItem, Product> productJoin = root.join("product");

        query.select(cb.construct(
                        PopularProductDTO.class,
                        productJoin.get("productId"),
                        productJoin.get("name"),
                        cb.sum(root.get("quantity")).as(Long.class)
                ))
                .where(cb.equal(orderJoin.get("orderStatus"), "Completed"))
                .groupBy(productJoin.get("productId"), productJoin.get("name"))
                .orderBy(cb.desc(cb.sum(root.get("quantity"))));

        return session.createQuery(query)
                .setMaxResults(count)
                .getResultList();
    }

    public List<Order> findOrdersPaginated(int page, int size) {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        root.fetch("user", JoinType.INNER); // to avoid LazyInitializationException

        query.select(root)
                .orderBy(cb.desc(root.get("datePlaced"))); // latest orders first

        return session.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }


    public Long getTotalSoldItems() {
        Session session = getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<OrderItem> root = query.from(OrderItem.class);
        Join<OrderItem, Order> orderJoin = root.join("order");

        query.select(cb.sum(root.get("quantity")).as(Long.class))
                .where(cb.equal(orderJoin.get("orderStatus"), "Completed"));

        Long result = session.createQuery(query).getSingleResult();
        return result != null ? result : 0L;
    }


}
