package com.example.e_commerce.repository;

import com.example.e_commerce.entities.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    Optional<CartItems> findByUserIdAndProductIdAndOrderId(Long userId, Long productId, Long orderId);
}
