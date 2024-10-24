package com.example.e_commerce.controller;

import com.example.e_commerce.dto.CartItemDto;
import com.example.e_commerce.dto.OrderDto;
import com.example.e_commerce.dto.PlaceOrderDto;
import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")

public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtoList = customerService.getAllProducts();
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/product/search/{title}")
    public ResponseEntity<List<ProductDto>> searchProductByTitle(@PathVariable String title) {
        List<ProductDto> productDtoList = customerService.getProductsByTitle(title);
        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping("/cart")
    public ResponseEntity<?> postProductToCart(@RequestBody CartItemDto cartItemDto) {
        return customerService.addProducttoCart(cartItemDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = customerService.getCartByUserId(userId);
        if (orderDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping
            ("/{userId}/deduct/{productId}")
    public ResponseEntity<OrderDto> addMinusOnProduct(@PathVariable Long userId, @PathVariable Long productId) {
        OrderDto orderDto = customerService.addMinusOnProduct(userId, productId);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/{userId}/add/{productId}")
    public ResponseEntity<OrderDto> addPlusOnProduct(@PathVariable Long userId, @PathVariable Long productId) {
        OrderDto orderDto = customerService.addPlusOnProduct(userId, productId);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        OrderDto orderDto = customerService.placeOrder(placeOrderDto);
        if (orderDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUserId(@PathVariable Long userId) {
        List<OrderDto> orderDtoList = customerService.getOrderByUserId(userId);
        return ResponseEntity.ok(orderDtoList);
    }

}
