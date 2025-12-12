package com.superdupermart.shopping_app.controller;


import com.superdupermart.shopping_app.dto.ProductUserView;
import com.superdupermart.shopping_app.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @PostMapping("/product/{productId}")
    public ResponseEntity<Map<String, String>> add(@PathVariable Long productId) {
        watchListService.addToWatchList(productId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Product added to watchlist.");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable Long productId) {
        watchListService.removeFromWatchList(productId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Product removed from watchlist.");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/products/all")
    public List<ProductUserView> viewAllInStock() {
        return watchListService.getInStockWatchListItems();
    }
}

