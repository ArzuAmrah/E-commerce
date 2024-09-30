package com.example.e_commerce.controller;

import com.example.e_commerce.dto.CartItemDto;
import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?>postProductToCart(@RequestBody CartItemDto cartItemDto) {
         return customerService.addProducttoCart(cartItemDto);
    }
}
