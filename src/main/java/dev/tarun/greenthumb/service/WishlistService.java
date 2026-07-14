package dev.tarun.greenthumb.service;

import dev.tarun.greenthumb.dto.WishlistItemRequestDTO;
import dev.tarun.greenthumb.dto.WishlistResponseDTO;

public interface WishlistService {
    WishlistResponseDTO getWishlist();
    WishlistResponseDTO addItem(WishlistItemRequestDTO request);
    WishlistResponseDTO removeItem(Long itemId);
}