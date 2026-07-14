package dev.tarun.greenthumb.service;

import dev.tarun.greenthumb.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO checkout();
}