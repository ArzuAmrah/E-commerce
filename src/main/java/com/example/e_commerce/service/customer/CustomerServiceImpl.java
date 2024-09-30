package com.example.e_commerce.service.customer;

import com.example.e_commerce.dto.CartItemDto;
import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.entities.CartItems;
import com.example.e_commerce.entities.OrderEntity;
import com.example.e_commerce.entities.ProductEntity;
import com.example.e_commerce.entities.UserEntity;
import com.example.e_commerce.enums.OrderStatus;
import com.example.e_commerce.repository.CartItemsRepository;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductEntity::getProductDto).collect(Collectors.toList());
    }

    private final OrderRepository orderRepository;

    private final CartItemsRepository cartItemsRepository;

    private final UserRepository userRepository;

    @Override
    public List<ProductDto> getProductsByTitle(String title) {
        return productRepository.findAllByNameContaining(title).stream().map(ProductEntity::getProductDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> addProducttoCart(CartItemDto cartItemDto) {
        OrderEntity pendingOrder = orderRepository.findByUserIdAndOrderStatus(cartItemDto.getUserId(), OrderStatus.PENDING);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(
                cartItemDto.getUserId(),
                cartItemDto.getProductId(),
                pendingOrder.getId());
        if (optionalCartItems.isPresent()) {
            CartItemDto productAllreadyExistInCart = new CartItemDto();
            productAllreadyExistInCart.setProductId(null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productAllreadyExistInCart);
        } else {
            Optional<ProductEntity> optionalProduct = productRepository.findById(cartItemDto.getProductId());
            Optional<UserEntity> optionalUser = userRepository.findById(cartItemDto.getUserId());
            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                ProductEntity product = optionalProduct.get();
                CartItems cartItem = new CartItems();
                cartItem.setProduct(product);
                cartItem.setUser(optionalUser.get());
                cartItem.setQuantity(1L);
                cartItem.setOrder(pendingOrder);
                cartItem.setPrice(product.getPrice());

                CartItems updatedCart = cartItemsRepository.save(cartItem);
                pendingOrder.setPrice(pendingOrder.getPrice() + product.getPrice());
                pendingOrder.getCartItems().add(cartItem);

                orderRepository.save(pendingOrder);
                CartItemDto updatedCartItemDto = new CartItemDto();
                updatedCartItemDto.setId(updatedCart.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItemDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }

    }
}
