package com.superdupermart.shopping_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PopularProductDTO {
    private Long productId;
    private String productName;
    private Long quantitySold;
}
