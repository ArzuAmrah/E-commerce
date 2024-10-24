package com.example.e_commerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemDto {

    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long id;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price cannot be less than zero")
    private Integer price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;

    @NotNull(message = "Product ID cannot be null")
    @Positive(message = "Product ID must be a positive number")
    private Long productId;

    @NotNull(message = "Order ID cannot be null")
    @Positive(message = "Order ID must be a positive number")
    private Long orderId;

    @NotEmpty(message = "Product name cannot be empty")
    private String productName;

    private byte[] returnedImage;

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;
}
