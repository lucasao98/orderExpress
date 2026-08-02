package com.foody.delivery.domain.order;

import com.foody.delivery.domain.address.Address;
import com.foody.delivery.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name="orders")
@Entity(name="orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {
    @Id
    private String order_id;
    private OrderStatus order_status;
    private BigDecimal total_price;

    public Order(OrderStatus order_status, BigDecimal total_price, User user, Address address) {
        this.order_status = order_status;
        this.total_price = total_price;
        this.user = user;
        this.address = address;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @PrePersist
    public void generateId() {
        if (this.order_id == null) {
            this.order_id = UUID.randomUUID().toString();
        }
    }

    public void update(OrderStatus newOrderStatus) {
        this.order_status = newOrderStatus;
    }
}
