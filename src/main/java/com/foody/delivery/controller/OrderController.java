package com.foody.delivery.controller;

import com.foody.delivery.domain.order.*;
import com.foody.delivery.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> index() {
        try {
            return ResponseEntity.ok(this.orderService.getOrders());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(@PathVariable String id) {
        try {
            return ResponseEntity.ok(this.orderService.getUserOrders(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CreateOrderDTO order) {
        try {
            this.orderService.createOrder(order);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public OrderResponseDTO show(@PathVariable String id) {
        try {
            return this.orderService.findOrder(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/tracking/{orderCode}")
    public OrderResponseDTO tracking(@PathVariable("orderCode") String orderCode) {
        try {
            return this.orderService.getOrderByCode(orderCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateStatus(@PathVariable String id, @RequestBody UpdateStatusDTO order_status) {
        try {
            this.orderService.updateStatus(id, order_status);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        try {
            this.orderService.deleteOrder(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
