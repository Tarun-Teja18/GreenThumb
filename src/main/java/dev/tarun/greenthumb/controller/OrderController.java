package dev.tarun.greenthumb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.tarun.greenthumb.dto.OrderResponseDTO;
import dev.tarun.greenthumb.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping                     // POST /api/orders = "check out my cart"
    public ResponseEntity<OrderResponseDTO> checkout() {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.checkout());
    }
}