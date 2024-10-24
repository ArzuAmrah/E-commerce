package com.example.e_commerce.service.customer;

import com.example.e_commerce.dto.CartItemDto;
import com.example.e_commerce.dto.OrderDto;
import com.example.e_commerce.dto.PlaceOrderDto;
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

import java.util.Date;
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

    @Override
    public OrderDto getCartByUserId(Long userId) {

        OrderEntity pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        List<CartItemDto> cartItemDtoList = pendingOrder.getCartItems().stream().map(CartItems::getCartItemDto).toList();
        OrderDto orderDto = new OrderDto();
        orderDto.setCartItemDtoList(cartItemDtoList);
        orderDto.setAmount(pendingOrder.getPrice());
        orderDto.setId(pendingOrder.getId());
        orderDto.setOrderStatus(pendingOrder.getOrderStatus());
        return orderDto;
    }

    @Override
    public OrderDto addMinusOnProduct(Long userId, Long productId) {
        OrderEntity pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        Optional<ProductEntity> optionalProduct = productRepository.findById(productId);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId, productId, pendingOrder.getId());
        if (optionalCartItems.isPresent() && optionalProduct.isPresent()) {
            CartItems cartItem = optionalCartItems.get();
            cartItem.setQuantity(optionalCartItems.get().getQuantity() - 1);
            pendingOrder.setPrice(pendingOrder.getPrice() - optionalProduct.get().getPrice());
            cartItemsRepository.save(cartItem);
            orderRepository.save(pendingOrder);
            return pendingOrder.getOrderDto();
        }
        return null;
    }

    @Override
    public OrderDto addPlusOnProduct(Long userId, Long productId) {
        OrderEntity pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        Optional<ProductEntity> optionalProduct = productRepository.findById(productId);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId, productId, pendingOrder.getId());
        if (optionalCartItems.isPresent() && optionalProduct.isPresent()) {
            CartItems cartItem = optionalCartItems.get();
            cartItem.setQuantity(optionalCartItems.get().getQuantity() + 1);
            pendingOrder.setPrice(pendingOrder.getPrice() + optionalProduct.get().getPrice());
            cartItemsRepository.save(cartItem);
            orderRepository.save(pendingOrder);
            return pendingOrder.getOrderDto();
        }
        return null;
    }

    @Override
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        OrderEntity existingOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.PENDING);
        Optional<UserEntity> optionalUser = userRepository.findById(placeOrderDto.getUserId());
        if (optionalUser.isPresent()) {
            existingOrder.setOrderStatus(OrderStatus.SUBMITTED);
            existingOrder.setAddress(placeOrderDto.getAddress());
            existingOrder.setDate(new Date());
            existingOrder.setPaymentType(placeOrderDto.getPayment());
            existingOrder.setDescription(placeOrderDto.getOrderDescription());
            existingOrder.setPrice(existingOrder.getPrice());
            orderRepository.save(existingOrder);
            OrderEntity order = new OrderEntity();
            order.setOrderStatus(OrderStatus.PENDING);
            order.setUser(optionalUser.get());
            order.setPrice(0L);
            orderRepository.save(order);
            return order.getOrderDto();
        }
        return null;
    }

    @Override
    public List<OrderDto> getOrderByUserId(Long userId) {
        return orderRepository.findAllByUserIdAndOrderStatus(userId, OrderStatus.SUBMITTED).stream().map(OrderEntity::getOrderDto).collect(Collectors.toList());
    }
}
