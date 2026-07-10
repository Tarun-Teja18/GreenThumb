package dev.tarun.greenthumb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.tarun.greenthumb.dto.CartItemRequestDTO;
import dev.tarun.greenthumb.dto.CartResponseDTO;
import dev.tarun.greenthumb.service.CartService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public CartResponseDTO getCart() {
        return cartService.getCart();
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(@Valid @RequestBody CartItemRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItem(request));
    }
}