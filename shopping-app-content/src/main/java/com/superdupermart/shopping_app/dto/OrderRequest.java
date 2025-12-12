package com.superdupermart.shopping_app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotEmpty(message = "Order must contain at least one item")
    @Valid // Ensures nested validation for each OrderItemRequest
    private List<OrderItemRequest> order;
}