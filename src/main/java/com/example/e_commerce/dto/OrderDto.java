package com.example.e_commerce.dto;

import com.example.e_commerce.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    @NotEmpty(message = "Order description cannot be empty")
    @Size(max = 255, message = "Order description must be less than or equal to 255 characters")
    private String orderDescription;

    @NotNull(message = "Cart items cannot be null")
    @Size(min = 1, message = "Order must contain at least one cart item")
    private List<@Valid CartItemDto> cartItemDtoList;

    @NotNull(message = "Order ID cannot be null")
    @Positive(message = "Order ID must be a positive number")
    private Long id;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date cannot be in the future")
    private Date date;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private Long amount;

    @NotEmpty(message = "Address cannot be empty")
    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    private String address;

    @NotNull(message = "Order status cannot be null")
    private OrderStatus orderStatus;

    @NotEmpty(message = "Payment type cannot be empty")
    @Pattern(regexp = "^(Credit Card|Debit Card|PayPal|Cash)$", message = "Payment type must be one of the following: Credit Card, Debit Card, PayPal, Cash")
    private String paymentType;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;
}
