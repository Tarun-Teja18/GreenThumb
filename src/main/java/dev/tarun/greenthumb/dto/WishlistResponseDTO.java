package dev.tarun.greenthumb.dto;

import java.util.List;

public record WishlistResponseDTO(
    Long wishlistId, 
    List<WishlistItemDTO> items
) {}              