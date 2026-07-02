package dev.tarun.greenthumb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.tarun.greenthumb.domain.OrderItem;

/**
 * Extending JpaRepository<OrderItem, Long> gives you save / findById / findAll /
 * deleteById / count ... for free. <Entity, type-of-its-@Id>.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}