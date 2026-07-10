package dev.tarun.greenthumb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.tarun.greenthumb.domain.Cart;
import dev.tarun.greenthumb.domain.CartItem;
import dev.tarun.greenthumb.domain.Plant;
import dev.tarun.greenthumb.enumeration.CartItemStatus;

/**
 * Extending JpaRepository<CartItem, Long> gives you save / findById / findAll /
 * deleteById / count ... for free. <Entity, type-of-its-@Id>.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartAndStatus(Cart cart, CartItemStatus status);
    Optional<CartItem> findByCartAndPlantAndStatus(Cart cart, Plant plant, CartItemStatus status);
}