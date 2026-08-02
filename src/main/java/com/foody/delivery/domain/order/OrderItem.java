package com.foody.delivery.domain.order;

import com.foody.delivery.domain.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name="order_items")
@Entity(name="order_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderItem {
    @Id
    private String order_item_id;
    private Integer quantity;
    private BigDecimal unit_price;
    private BigDecimal subtotal;

    public OrderItem(Integer quantity, BigDecimal unit_price, BigDecimal subtotal, Order order, Item item) {
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.subtotal = subtotal;
        this.order = order;
        this.item = item;
    }

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @PrePersist
    public void generateId() {
        if (this.order_item_id == null) {
            this.order_item_id = UUID.randomUUID().toString();
        }
    }
}
