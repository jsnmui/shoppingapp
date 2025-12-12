package com.superdupermart.shopping_app.dto;


import lombok.Data;

import java.util.Date;

@Data
public class OrderUserViewDTO {
    private Long orderId;
    private Date datePlaced;
    private String orderStatus;
}