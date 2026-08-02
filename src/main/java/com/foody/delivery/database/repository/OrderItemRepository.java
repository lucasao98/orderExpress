package com.foody.delivery.database.repository;

import com.foody.delivery.domain.address.Address;
import com.foody.delivery.domain.order.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, String> {
    @Query("SELECT oi FROM order_items oi WHERE oi.order.order_id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") String orderId);
}
