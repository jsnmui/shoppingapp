package com.superdupermart.shopping_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long itemId;
    private Long userId;
    private String username;
    private int quantity;
    private double purchasedPrice;
    private Long productId;
    private String productName;

    @JsonIgnore
    private Double wholesalePrice; // captured but hidden from normal users
}