package com.example.e_commerce.service.customer;

import com.example.e_commerce.dto.CartItemDto;
import com.example.e_commerce.dto.OrderDto;
import com.example.e_commerce.dto.PlaceOrderDto;
import com.example.e_commerce.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByTitle(String title);

    ResponseEntity<?> addProducttoCart(CartItemDto cartItemDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto addMinusOnProduct(Long userId, Long productId);

    OrderDto addPlusOnProduct(Long userId, Long productId);

    OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto>getOrderByUserId(Long userId);

}
