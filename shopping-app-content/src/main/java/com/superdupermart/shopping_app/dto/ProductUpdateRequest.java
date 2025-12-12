package com.superdupermart.shopping_app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductUpdateRequest {
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Positive(message = "Wholesale price must be positive")
    private Double wholesalePrice;

    @Positive(message = "Retail price must be positive")
    private Double retailPrice;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
}
