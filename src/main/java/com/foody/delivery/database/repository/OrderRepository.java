package com.foody.delivery.database.repository;

import com.foody.delivery.domain.order.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, String> {
    @Query("SELECT o FROM orders o WHERE o.user.user_id = :userId")
    List<Order> findByUserId(@Param("userId") String userId);
}
