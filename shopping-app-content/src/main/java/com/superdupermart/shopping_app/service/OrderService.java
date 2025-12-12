package com.superdupermart.shopping_app.service;

import com.superdupermart.shopping_app.dao.OrderDao;
import com.superdupermart.shopping_app.dto.*;
import com.superdupermart.shopping_app.entity.*;
import com.superdupermart.shopping_app.exception.NotEnoughInventoryException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import com.superdupermart.shopping_app.dao.UserDao;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;



    @Transactional
    public Order placeOrder(OrderRequest request) {
        Session session = sessionFactory.getCurrentSession();

        // Get username from Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();


        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Order order = new Order();
        order.setUser(user);
        order.setDatePlaced(new Date());
        order.setOrderStatus("Processing");

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemReq : request.getOrder()) {
            Product product = session.get(Product.class, itemReq.getProductId());
            if (product == null) throw new RuntimeException("Product not found");

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new NotEnoughInventoryException("Not enough stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - itemReq.getQuantity());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPurchasedPrice(product.getRetailPrice());
            item.setWholesalePrice(product.getWholesalePrice());

            orderItems.add(item);
        }

        order.setItems(orderItems);
        session.persist(order);

        return order;
    }



    private boolean isCurrentUserAdmin() {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }

        String username = auth.getName();
        User user = userService.getUserByUsername(username);
        return user != null && user.getRole() == 1;
    }


    public OrderUserViewDTO mapToUserViewDTO(Order order) {
        OrderUserViewDTO dto = new OrderUserViewDTO();
        dto.setOrderId(order.getOrderId());
        dto.setDatePlaced(order.getDatePlaced());
        dto.setOrderStatus(order.getOrderStatus());
        return dto;
    }


    public OrderDTO mapToOrderDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setDatePlaced(order.getDatePlaced());
        dto.setOrderStatus(order.getOrderStatus());

        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        boolean isAdmin = isCurrentUserAdmin();

        for (OrderItem item : order.getItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setItemId(item.getItemId());
            itemDTO.setUserId(item.getOrder().getUser().getUserId());
            itemDTO.setUsername(item.getOrder().getUser().getUsername());
            itemDTO.setItemId(item.getItemId());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPurchasedPrice(item.getPurchasedPrice());
            itemDTO.setProductId(item.getProduct().getProductId());
            itemDTO.setProductName(item.getProduct().getName());

            if (isAdmin) {
                itemDTO.setWholesalePrice(item.getProduct().getWholesalePrice());
            }

            itemDTOs.add(itemDTO);
        }

        dto.setItems(itemDTOs);
        return dto;
    }



    @Transactional
    public void cancelOrder(Long orderId) {
        Session session = sessionFactory.getCurrentSession();


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();


        User user = userDao.findByUserName(username);
        if (user == null) throw new RuntimeException("User not found");

        Order order = session.get(Order.class, orderId);
        if (order == null) throw new NoSuchElementException("Order not found");


        String status = order.getOrderStatus();
        if ("Canceled".equals(status)) throw new IllegalStateException("Order already canceled.");
        if ("Completed".equals(status)) throw new IllegalStateException("Completed order cannot be canceled.");

        // Authorization: User can cancel only their own orders
        boolean isAdmin = user.getRole() == 1;
        if (!isAdmin && !order.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalStateException("You can only cancel your own orders.");
        }

        // Restore inventory
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            session.merge(product);
        }

        // Update order status
        order.setOrderStatus("Canceled");
        session.merge(order);
    }






    @Transactional
    public List<Order> getAllOrders() {
        // Get the current user's username from Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();


        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new RuntimeException("Authenticated user not found.");
        }

        return orderDao.findOrdersByUserId(user.getUserId());
    }

    @Transactional
    public OrderDTO getOrderById(Long orderId) {


        Order order = orderDao.findOrderById(orderId);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        // If user is not admin, only allow access to their own orders
        if (user.getRole() != 1 && !order.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("You are not authorized to view this order.");
        }

        return mapToOrderDTO(order);
    }
    @Transactional
    public List<RecentProductDTO> getRecentProducts(int limit) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        return orderDao.findRecentProductsByUserId(user.getUserId(), limit);
    }


    @Transactional
    public List<FrequentProductDTO> getMostFrequentProducts(int limit) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<FrequentProductDTO> products = orderDao.findMostFrequentProductsByUserId(user.getUserId(), limit);
        return products.stream()
                .map(p -> new FrequentProductDTO(p.getProductId(), p.getProductName(), p.getQuantity()))
                .toList();
    }



    @Transactional
    public List<OrderAdminViewDTO> getAllOrdersForAdmin() {
        List<Order> orders = orderDao.findAllOrdersWithUsers();
        List<OrderAdminViewDTO> dtos = new ArrayList<>();

        for (Order order : orders) {
            OrderAdminViewDTO dto = new OrderAdminViewDTO();
            dto.setOrderId(order.getOrderId());
            dto.setDatePlaced(order.getDatePlaced());
            dto.setOrderStatus(order.getOrderStatus());
            dtos.add(dto);
        }

        return dtos;
    }

    @Transactional
    public void completeOrder(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, orderId);

        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }

        if (!"Processing".equalsIgnoreCase(order.getOrderStatus())) {
            throw new IllegalStateException("Only Processing orders can be marked as Completed");
        }

        order.setOrderStatus("Completed");
        session.merge(order);
    }

    @Transactional(readOnly = true)
    public List<ProfitProductDTO> getMostProfitableProducts(int count) {
        return orderDao.findTopProfitableProducts(count);
    }

    @Transactional(readOnly = true)
    public List<PopularProductDTO> getMostPopularProducts(int count) {
        return orderDao.findMostPopularProducts(count);
    }

    @Transactional (readOnly = true)
    public List<OrderAdminViewDTO> getOrdersForAdminPaginated(int page, int size) {
        List<Order> orders = orderDao.findOrdersPaginated(page, size);

        return orders.stream().map(order -> {
            OrderAdminViewDTO dto = new OrderAdminViewDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setDatePlaced(order.getDatePlaced());
            return dto;
        }).collect(Collectors.toList());
    }
    @Transactional (readOnly = true)
    public Long getTotalSoldItems() {
        return orderDao.getTotalSoldItems();
    }
}