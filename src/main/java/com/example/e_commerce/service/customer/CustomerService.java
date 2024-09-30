package com.example.e_commerce.service.customer;

import com.example.e_commerce.dto.CartItemDto;
import com.example.e_commerce.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByTitle(String title);

    ResponseEntity<?> addProducttoCart(CartItemDto cartItemDto);

}
