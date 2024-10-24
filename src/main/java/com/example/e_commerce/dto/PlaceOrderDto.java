package com.example.e_commerce.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlaceOrderDto {

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be a positive number")
    private Long userId;

    @NotEmpty(message = "Address cannot be empty")
    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    private String address;

    @Size(max = 255, message = "Order description must be less than or equal to 255 characters")
    private String orderDescription;

    @NotEmpty(message = "Payment type cannot be empty")
    @jakarta.validation.constraints.Pattern(regexp = "^(Credit Card|Debit Card|PayPal|Cash)$", message = "Payment type must be one of the following: Credit Card, Debit Card, PayPal, Cash")
    private String payment;
}
