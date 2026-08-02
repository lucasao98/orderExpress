package com.foody.delivery.domain.order;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Integer quantity,
        String item_name,
        BigDecimal item_price
) {
    public OrderItemResponseDTO(OrderItem orderItem) {
        this(orderItem.getQuantity(), orderItem.getItem().getItem_name(), orderItem.getUnit_price());
    }
}