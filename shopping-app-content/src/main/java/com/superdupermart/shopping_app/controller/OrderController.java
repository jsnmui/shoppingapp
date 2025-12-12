package com.superdupermart.shopping_app.controller;



import com.superdupermart.shopping_app.dto.OrderAdminViewDTO;
import com.superdupermart.shopping_app.dto.OrderDTO;
import com.superdupermart.shopping_app.dto.OrderRequest;
import com.superdupermart.shopping_app.dto.OrderUserViewDTO;
import com.superdupermart.shopping_app.entity.Order;
import com.superdupermart.shopping_app.entity.User;
import com.superdupermart.shopping_app.exception.NotEnoughInventoryException;
import com.superdupermart.shopping_app.service.OrderService;
import com.superdupermart.shopping_app.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

//    @PostMapping
//    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest request) {
//        try {
//            Order order = orderService.placeOrder(request);
//          //  OrderDTO orderDTO = orderService.mapToOrderDTO(order);
//            OrderUserViewDTO orderDTO = orderService.mapToUserViewDTO(order);
//            return ResponseEntity.ok(orderDTO);
//        } catch (NotEnoughInventoryException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body("Something went wrong.");
//        }
//    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest request) {
        try {
            Order order = orderService.placeOrder(request);
            OrderUserViewDTO orderDTO = orderService.mapToUserViewDTO(order);
            return ResponseEntity.ok(orderDTO);
        } catch (NotEnoughInventoryException e) {

            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Something went wrong."));
        }
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok(Map.of("message", "Order canceled successfully."));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to cancel order."));
        }
    }




    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long orderId) {

        OrderDTO dto = orderService.getOrderById(orderId);
        return ResponseEntity.ok(dto);
    }


//    @GetMapping("/all")
//    public ResponseEntity<?> getAllOrders() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        User user = userService.getUserByUsername(username);
//
//        if (user.getRole() == 1) {
//
//            List<OrderAdminViewDTO> adminOrders = orderService.getAllOrdersForAdmin();
//            return ResponseEntity.ok(adminOrders);
//        } else {
//
//            List<Order> userOrders = orderService.getAllOrders();
//            List<OrderDTO> userDtos = userOrders.stream()
//                    .map(orderService::mapToOrderDTO)
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(userDtos);
//        }
//    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10000") int size) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        if (user.getRole() == 1) {
            // Sellers get paginated orders
            List<OrderAdminViewDTO> adminOrders = orderService.getOrdersForAdminPaginated(page, size);
            return ResponseEntity.ok(adminOrders);

        } else {
            // Regular users
            List<Order> userOrders = orderService.getAllOrders();;
            List<OrderUserViewDTO> userDtos = userOrders.stream()
                    .map(orderService::mapToUserViewDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userDtos);
        }
    }
//    @PatchMapping("/{id}/complete")
//    @PreAuthorize("hasAuthority('ADMIN')") // Restrict to admin only
//    public ResponseEntity<?> completeOrder(@PathVariable Long id) {
//        try {
//            orderService.completeOrder(id);
//            return ResponseEntity.ok("Order marked as Completed");
//        } catch (IllegalStateException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> completeOrder(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            orderService.completeOrder(id);
            response.put("message", "Order marked as Completed");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        } catch (EntityNotFoundException e) {
            response.put("message", "Order not found");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
