package com.superdupermart.shopping_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RecentProductDTO {
    private Long productId;
    private String productName;
    private Date datePurchased;
}
