package com.foody.delivery.domain.item;

import java.math.BigDecimal;

public record ItemDTO(String item_name, Integer quantity_available, BigDecimal price) {
}
