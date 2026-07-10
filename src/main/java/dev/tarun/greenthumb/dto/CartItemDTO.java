package dev.tarun.greenthumb.dto;

public record CartItemDTO(
        Long id,
        Long plantId,
        String plantName,
        Integer quantity,
        Double amount
) {}