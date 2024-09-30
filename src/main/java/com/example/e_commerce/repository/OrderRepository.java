package com.example.e_commerce.repository;

import com.example.e_commerce.entities.OrderEntity;
import com.example.e_commerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
