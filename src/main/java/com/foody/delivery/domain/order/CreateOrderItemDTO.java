package com.foody.delivery.domain.order;

import java.math.BigDecimal;

public record CreateOrderItemDTO(String item_id, Integer quantity, BigDecimal unit_price) {
}
