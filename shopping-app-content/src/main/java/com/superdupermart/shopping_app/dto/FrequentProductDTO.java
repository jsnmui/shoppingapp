package com.superdupermart.shopping_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class FrequentProductDTO {
    private Long productId;
    private String productName;
    private Long quantity; // number of times purchased

}