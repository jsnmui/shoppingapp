package com.superdupermart.shopping_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitProductDTO {
    private Long productId;
    private String productName;
    private double totalProfit;

}