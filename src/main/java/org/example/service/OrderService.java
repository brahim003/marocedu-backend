package org.example.service;

import org.example.model.DTO.OrderItemRequestDTO;
import org.example.model.DTO.OrderRequestDTO;
import org.example.model.entity.Order;
import org.example.model.entity.OrderItem;
import org.example.model.entity.Supply;
import org.example.repository.OrderRepository;
import org.example.repository.SupplyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final SupplyRepository supplyRepository;

    public OrderService(OrderRepository orderRepository, SupplyRepository supplyRepository) {
        this.orderRepository = orderRepository;
        this.supplyRepository = supplyRepository;
    }

    // --- 1. CRÉATION ---
    @Transactional
    public void createOrder(OrderRequestDTO request) {
        // ... (L-Code L-Kamil li 3andk) ...
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setNotes(request.getNotes());

        order.setTotalAmount(request.getTotalAmount());
        order.setPackagingRequested(request.getPackagingRequested());

        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        for (OrderItemRequestDTO itemDto : request.getItems()) {
            Supply supply = supplyRepository.findById(itemDto.getSupplyId())
                    .orElseThrow(() -> new RuntimeException("Supply not found ID: " + itemDto.getSupplyId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setOptionId(itemDto.getOptionId());
            orderItem.setSupply(supply);

            // Copier les infos fixes
            orderItem.setIsbn(supply.getIsbn());
            orderItem.setPosition(supply.getPosition());

            order.addItem(orderItem);
        }

        orderRepository.save(order);
    }

    // --- 2. LECTURE ---
    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec l'ID : " + id));
    }

    // ----------------------------------------------------------------------------------

    /**
     * ✅ NOUVEAU: Mettre à jour le statut d'une commande (L-Wadifa L-Matlouba)
     */
    @Transactional
    public Order updateStatus(Long orderId, String newStatus) {
        // 1. Trouver L-Order b-L-ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec l'ID : " + orderId));

        // 2. Taḥdid L-Status L-Jdid (n-dirou lih Majuscule bach ykoun mzyan)
        order.setStatus(newStatus.toUpperCase());

        // 3. L-7ifd w L-Rdd L-Order L-Muḥaddat
        return orderRepository.save(order);
    }
}