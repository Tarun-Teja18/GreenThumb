package dev.tarun.greenthumb.service;

import dev.tarun.greenthumb.dto.CartItemRequestDTO;
import dev.tarun.greenthumb.dto.CartResponseDTO;

public interface CartService {
    CartResponseDTO getCart();
    CartResponseDTO addItem(CartItemRequestDTO request);
}