package dev.tarun.greenthumb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.tarun.greenthumb.domain.Cart;
import dev.tarun.greenthumb.domain.CartItem;
import dev.tarun.greenthumb.domain.OrderItem;
import dev.tarun.greenthumb.domain.OrderSummary;
import dev.tarun.greenthumb.domain.User;
import dev.tarun.greenthumb.dto.OrderItemDTO;
import dev.tarun.greenthumb.dto.OrderResponseDTO;
import dev.tarun.greenthumb.enumeration.CartItemStatus;
import dev.tarun.greenthumb.enumeration.CartStatus;
import dev.tarun.greenthumb.enumeration.OrderSummaryStatus;
import dev.tarun.greenthumb.exception.BadRequestException;
import dev.tarun.greenthumb.exception.NotFoundException;
import dev.tarun.greenthumb.repository.CartItemRepository;
import dev.tarun.greenthumb.repository.CartRepository;
import dev.tarun.greenthumb.repository.OrderItemRepository;
import dev.tarun.greenthumb.repository.OrderSummaryRepository;
import dev.tarun.greenthumb.repository.UserRepository;
import dev.tarun.greenthumb.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderSummaryRepository orderSummaryRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
                            OrderSummaryRepository orderSummaryRepository, OrderItemRepository orderItemRepository,
                            UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderSummaryRepository = orderSummaryRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional      // ← all writes below commit together, or all roll back
    public OrderResponseDTO checkout() {
        User user = getCurrentUser();

        // 1. the user's active cart + its items
        Cart cart = cartRepository.findByUserAndStatus(user, CartStatus.ACTIVE)
                .orElseThrow(() -> new BadRequestException("No active cart to check out"));
        List<CartItem> cartItems = cartItemRepository.findByCartAndStatus(cart, CartItemStatus.ADDED);
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        // 2. create the order shell
        OrderSummary order = new OrderSummary();
        order.setOrderId("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus(OrderSummaryStatus.PENDING);
        order.setUser(user);
        order.setCart(cart);
        order.setTotalAmount(0.0);           // temp; set after we sum lines
        orderSummaryRepository.save(order);  // save first so it has an id

        // 3. one OrderItem per cart line, freezing the price
        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrderSummary(order);
            oi.setPlant(ci.getPlant());
            oi.setQuantity(ci.getQuantity());
            oi.setSoldPrice(ci.getPlant().getSellingPrice());   // ← SNAPSHOT the price now
            orderItems.add(oi);
            total += oi.getSoldPrice() * oi.getQuantity();
        }
        orderItemRepository.saveAll(orderItems);

        // 4. finalize total + close the cart
        order.setTotalAmount(total);
        orderSummaryRepository.save(order);
        cart.setStatus(CartStatus.ORDERED);   // this cart is done; next add-to-cart makes a fresh one
        cartRepository.save(cart);

        return toDto(order, orderItems);
    }

    // ---- helpers ----
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found: " + email));
    }

    private OrderResponseDTO toDto(OrderSummary order, List<OrderItem> items) {
        List<OrderItemDTO> itemDtos = items.stream()
                .map(oi -> new OrderItemDTO(oi.getId(), oi.getPlant().getId(),
                        oi.getPlant().getName(), oi.getQuantity(), oi.getSoldPrice()))
                .toList();
        return new OrderResponseDTO(order.getId(), order.getOrderId(),
                order.getStatus().name(), order.getTotalAmount(), itemDtos);
    }
}