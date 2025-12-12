package com.superdupermart.shopping_app.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSellerView {
    private Long productId;
    private String name;
    private double retailPrice;
}
