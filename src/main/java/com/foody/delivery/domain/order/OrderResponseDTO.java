package com.foody.delivery.domain.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        String orderId,
        String userName,
        String userEmail,
        String addressStreet,
        Integer addressNumber,
        String addressCity,
        OrderStatus orderStatus,
        BigDecimal totalPrice,
        List<OrderItemResponseDTO> items
) {
    public OrderResponseDTO(Order order, List<OrderItem> orderItems) {
        this(
                order.getOrder_id(),
                order.getUser().getName(),
                order.getUser().getEmail(),
                order.getAddress().getStreet(),
                order.getAddress().getNumber(),
                order.getAddress().getCity(),
                order.getOrder_status(),
                order.getTotal_price(),
                orderItems.stream()
                        .map(OrderItemResponseDTO::new)
                        .toList()
        );
    }
}