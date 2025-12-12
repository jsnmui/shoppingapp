package com.superdupermart.shopping_app.controller;

import com.superdupermart.shopping_app.dto.*;
import com.superdupermart.shopping_app.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.superdupermart.shopping_app.entity.Product;
import com.superdupermart.shopping_app.entity.User;
import com.superdupermart.shopping_app.security.JwtProvider;
import com.superdupermart.shopping_app.service.ProductService;
import com.superdupermart.shopping_app.service.UserService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(HttpServletRequest request) {
        String username = jwtProvider.extractUserFromRequest(request);
        User user = userService.getUserByUsername(username);

        List<Product> products;

        if (user.getRole() == 1) { // ADMIN / seller
            products = productService.getAllProducts(); // show all products
            List<ProductSellerView> sellerViews = products.stream()
                    .map(p -> new ProductSellerView(
                            p.getProductId(),
                            p.getName(),
                            p.getRetailPrice()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(sellerViews);
        } else { // BUYER
            products = productService.getAllInStockProducts(); // only in-stock products
            List<ProductUserView> buyerViews = products.stream()
                    .map(p -> new ProductUserView(
                            p.getProductId(),
                            p.getName(),
                            p.getRetailPrice()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(buyerViews);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);


        if (user.getRole() == 1) { // Admin
            ProductSellerDetailedView sellerView = new ProductSellerDetailedView(
                    product.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    product.getRetailPrice(),
                    product.getWholesalePrice(),
                    product.getQuantity()
            );
            return ResponseEntity.ok(sellerView);
        } else { // Normal User
            ProductUserDetailedView userView = new ProductUserDetailedView(
                    product.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    product.getRetailPrice()
            );
            return ResponseEntity.ok(userView);
        }
    }

    @GetMapping("/recent/{limit}")
    public List<RecentProductDTO> getRecentProducts(@PathVariable int limit) {
        List<RecentProductDTO> recentProducts = orderService.getRecentProducts(limit);
        return recentProducts.stream()
                .map(p -> new RecentProductDTO(
                        p.getProductId(),
                        p.getProductName(),
                        p.getDatePurchased()))
                .collect(Collectors.toList());
    }

    @GetMapping("/frequent/{count}")
    public List<FrequentProductDTO> getMostFrequentProducts(@PathVariable("count") int count) {
        return orderService.getMostFrequentProducts(count);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProductAsAdmin(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest updateRequest) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);

        // Only admin can access
        if (user.getRole() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only admin can update product details.");
        }

        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        if (updateRequest.getName() != null) {
            product.setName(updateRequest.getName());
        }
        if (updateRequest.getWholesalePrice() != null) {
            product.setWholesalePrice(updateRequest.getWholesalePrice());
        }
        if (updateRequest.getRetailPrice() != null) {
            product.setRetailPrice(updateRequest.getRetailPrice());
        }
        if (updateRequest.getDescription() != null) {
            product.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getQuantity() != null) {
            product.setQuantity(updateRequest.getQuantity());
        }

        productService.updateProduct(product); // persist the changes


        return ResponseEntity.ok(product);
    }




    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductAddRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);

        // Only admin can access
        if (user.getRole() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only sellers can add products"));
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setWholesalePrice(request.getWholesalePrice());
        product.setRetailPrice(request.getRetailPrice());
        product.setQuantity(request.getQuantity());

        productService.saveProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }



    @GetMapping("/profit/{count}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProfitProductDTO>> getMostProfitableProducts(@PathVariable int count) {
        List<ProfitProductDTO> products = orderService.getMostProfitableProducts(count);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/popular/{count}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PopularProductDTO>> getMostPopularProducts(@PathVariable int count) {
        return ResponseEntity.ok(orderService.getMostPopularProducts(count));
    }

    @GetMapping("/sold/total")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Long> getTotalItemsSold() {
        Long totalSold = orderService.getTotalSoldItems();
        return ResponseEntity.ok(totalSold);
    }

}