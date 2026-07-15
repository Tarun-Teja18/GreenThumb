package dev.tarun.greenthumb.service;

import java.util.List;

import dev.tarun.greenthumb.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO checkout();
    List<OrderResponseDTO> getMyOrders();
    OrderResponseDTO getOrder(Long id);
}