package com.superdupermart.shopping_app.dto;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddRequest {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Wholesale price must be positive")
    private double wholesalePrice;

    @Positive(message = "Retail price must be positive")
    private double retailPrice;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
}
