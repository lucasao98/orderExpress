package com.foody.delivery.domain.order;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderDTO(OrderStatus order_status, BigDecimal total_price, String user_id, String address_id, List<CreateOrderItemDTO> items) {
}
