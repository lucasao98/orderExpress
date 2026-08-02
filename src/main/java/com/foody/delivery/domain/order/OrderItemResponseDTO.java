package com.foody.delivery.domain.order;

public record OrderItemResponseDTO(
        Integer quantity
) {
    public OrderItemResponseDTO(OrderItem orderItem) {
        this(orderItem.getQuantity());
    }
}