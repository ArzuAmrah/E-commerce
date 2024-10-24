package com.example.e_commerce.entities;

import com.example.e_commerce.dto.OrderDto;
import com.example.e_commerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String address;

    private String paymentType;

    private Date date;

    private Long price;

    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setAmount(price);
        orderDto.setAddress(address);
        orderDto.setPaymentType(paymentType);
        orderDto.setUsername(user.getName());
        orderDto.setDate(date);
        orderDto.setOrderDescription(description);

        return orderDto;
    };

}
