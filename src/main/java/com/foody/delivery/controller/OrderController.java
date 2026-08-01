package com.foody.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    @GetMapping
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("ORDERS", HttpStatus.OK);
    }
}
