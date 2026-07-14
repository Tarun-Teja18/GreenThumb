package dev.tarun.greenthumb.dto;

public record OrderItemDTO(
    Long id, 
    Long plantId, 
    String plantName, 
    Integer quantity, 
    Double soldPrice
) {}