package com.foody.delivery.service;

import com.foody.delivery.database.repository.*;
import com.foody.delivery.domain.address.Address;
import com.foody.delivery.domain.item.Item;
import com.foody.delivery.domain.order.*;
import com.foody.delivery.domain.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, AddressRepository addressRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderResponseDTO> getOrders() {
        List<Order> orders = (List<Order>) this.orderRepository.findAll();

        return orders.stream()
                .map(order -> {
                    List<OrderItem> items = this.orderItemRepository.findByOrderId(order.getOrder_id());
                    return new OrderResponseDTO(order, items);
                })
                .toList();
    }

    public List<OrderResponseDTO> getUserOrders(String user_id) throws Exception {
        Optional <User> foundUser = this.userRepository.findById(user_id);

        if(foundUser.isEmpty()){
            throw new Exception("User not found");
        }

        List<Order> orders = (List<Order>) this.orderRepository.findAll();

        return orders.stream()
                .map(order -> {
                    List<OrderItem> items = this.orderItemRepository.findByOrderId(order.getOrder_id());
                    return new OrderResponseDTO(order, items);
                })
                .toList();
    }

    public boolean createOrder(CreateOrderDTO order) throws Exception {
        Optional <User> foundUser = this.userRepository.findById(order.user_id());
        Optional < Address> foundAddress = this.addressRepository.findById(order.address_id());

        String orderIdCreated = "";

        if(foundUser.isEmpty()){
            throw new Exception("User not found");
        }

        if(foundAddress.isEmpty()){
            throw new Exception("Address not found");
        }

        if(order.total_price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Total price must not be less than or equal to 0");
        }

        if(order.items().isEmpty()) {
            throw new Exception("Items are required");
        }

        Map<String, Integer> quantityByItemId = order.items().stream()
                .collect(Collectors.toMap(CreateOrderItemDTO::item_id, CreateOrderItemDTO::quantity));

        List<Item> foundItems = (List<Item>) this.itemRepository.findAllById(quantityByItemId.keySet());

        if (foundItems.size() != quantityByItemId.size()) {
            List<String> foundIds = foundItems.stream()
                    .map(Item::getItem_id)
                    .toList();

            List<String> missingIds = quantityByItemId.keySet().stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new Exception("Products not found: " + missingIds);
        }

        try {
            Order newOrder = new Order(order.order_status(), order.total_price(), foundUser.get(), foundAddress.get());

            Order orderCreated = this.orderRepository.save(newOrder);
            orderIdCreated = orderCreated.getOrder_id();

            List<OrderItem> orderItems = foundItems.stream()
                    .map(item -> {
                        Integer quantity = quantityByItemId.get(item.getItem_id());
                        BigDecimal unitPrice = item.getPrice();
                        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

                        return new OrderItem(quantity, unitPrice, subtotal, orderCreated, item);
                    })
                    .toList();

            this.orderItemRepository.saveAll(orderItems);

            return true;
        } catch (Exception e) {
            this.orderRepository.deleteById(orderIdCreated);
            throw new RuntimeException(e);
        }
    }

    public OrderResponseDTO findOrder(String order_id) throws Exception {
       Optional<Order> foundOrder = this.orderRepository.findById(order_id);

       if(foundOrder.isEmpty()){
           throw new Exception("Order not found");
       }

        List<OrderItem> items = this.orderItemRepository.findByOrderId(foundOrder.get().getOrder_id());

        return new OrderResponseDTO(foundOrder.get(), items);

    }

    public boolean updateStatus(String order_id, UpdateStatusDTO order_status) throws Exception {
       Optional<Order> foundOrder = this.orderRepository.findById(order_id);

       if(foundOrder.isEmpty()){
           throw new Exception("Order not found");
       }

       Order order = foundOrder.get();

       order.update(order_status.order_status());

       this.orderRepository.save(order);

       return true;
    }

    public boolean deleteOrder(String order_id) throws Exception {
        Optional<Order> foundOrder = this.orderRepository.findById(order_id);

        if (foundOrder.isEmpty()) {
            throw new Exception("Order not found");
        }

        List<OrderItem> orderItems = this.orderItemRepository.findByOrderId(order_id);
        this.orderItemRepository.deleteAll(orderItems);

        this.orderRepository.deleteById(order_id);

        return true;
    }
}
