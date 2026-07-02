package dev.tarun.greenthumb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.tarun.greenthumb.domain.WishlistItem;

/**
 * Extending JpaRepository<WishlistItem, Long> gives you save / findById / findAll /
 * deleteById / count ... for free. <Entity, type-of-its-@Id>.
 */
@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
}