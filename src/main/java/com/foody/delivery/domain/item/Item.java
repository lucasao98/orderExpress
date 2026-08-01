package com.foody.delivery.domain.item;

import com.foody.delivery.domain.user.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name="itens")
@Entity(name="itens")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {
    @Id
    private String item_id;
    private String item_name;
    private Integer quantity_available;
    private BigDecimal price;

    public Item(String item_name, Integer quantity_available, BigDecimal price) {
        this.item_name = item_name;
        this.quantity_available = quantity_available;
        this.price = price;
    }

    @PrePersist
    public void generateId() {
        if (this.item_id == null) {
            this.item_id = UUID.randomUUID().toString();
        }
    }

    public void update(String itemName, Integer quantityAvailable, BigDecimal price) {
        this.item_name = itemName;
        this.quantity_available = quantityAvailable;
        this.price = price;
    }
}