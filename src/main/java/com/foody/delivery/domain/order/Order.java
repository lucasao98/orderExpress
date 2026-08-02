package com.foody.delivery.domain.order;

import com.foody.delivery.domain.address.Address;
import com.foody.delivery.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.security.SecureRandom;

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
    private String order_tracking_code;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public Order(OrderStatus order_status, BigDecimal total_price, User user, Address address) {
        this.order_status = order_status;
        this.total_price = total_price;
        this.user = user;
        this.address = address;
        generateOrderTrackingCode();
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

    public void generateOrderTrackingCode() {
        if (this.order_tracking_code == null) {
            StringBuilder code = new StringBuilder(6);

            for (int i = 0; i < 6; i++) {
                code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }

            this.order_tracking_code = code.toString();
        }
    }

    public void update(OrderStatus newOrderStatus) {
        this.order_status = newOrderStatus;
    }
}
