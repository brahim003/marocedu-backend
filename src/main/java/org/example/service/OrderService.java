package org.example.service;

import org.example.model.DTO.OrderItemRequestDTO;
import org.example.model.DTO.OrderRequestDTO;
import org.example.model.entity.Order;
import org.example.model.entity.OrderItem;
import org.example.model.entity.Option;
import org.example.model.entity.Supply;
import org.example.repository.OptionRepository;
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
    private final OptionRepository optionRepository;

    // Injection via Constructor
    public OrderService(OrderRepository orderRepository,
                        SupplyRepository supplyRepository,
                        OptionRepository optionRepository) {
        this.orderRepository = orderRepository;
        this.supplyRepository = supplyRepository;
        this.optionRepository = optionRepository;
    }

    // --- 1. CRÉATION (Create Order) ---
    @Transactional
    public void createOrder(OrderRequestDTO request) {
        Order order = new Order();
        // ... (Mapping basic fields) ...
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

            // ✅ Option Handling
            orderItem.setOptionId(itemDto.getOptionId());

            orderItem.setSupply(supply);
            orderItem.setIsbn(supply.getIsbn());
            orderItem.setPosition(supply.getPosition());

            order.addItem(orderItem);
        }

        orderRepository.save(order);
    }

    // --- 2. LECTURE (Read) ---
    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec l'ID : " + id));

        // ⚡ HACK: Force Initialization of Items
        // This ensures Hibernate fetches items before closing the transaction
        order.getItems().size();

        return order;
    }

    // ----------------------------------------------------------------------------------
    // 🔥🔥 STOCK MANAGEMENT LOGIC (CORRECTED) 🔥🔥
    // ----------------------------------------------------------------------------------

    @Transactional
    public Order updateStatus(Long orderId, String newStatusRaw) {
        Order order = getOrderById(orderId);

        // 1. CLEANUP: Convert to UPPERCASE and remove spaces
        String oldStatus = (order.getStatus() == null) ? "" : order.getStatus().trim().toUpperCase();
        String newStatus = (newStatusRaw == null) ? "" : newStatusRaw.trim().toUpperCase();

        // Debugging LOG
        System.out.println("🔄 Status Update | Order ID: " + orderId + " | Old: " + oldStatus + " -> New: " + newStatus);

        // Avoid unnecessary work
        if (oldStatus.equals(newStatus)) {
            return order;
        }

        // 👉 SCÉNARIO 1 : ANNULATION (RESTORE STOCK)
        // ✅ FIX: Use "CANCELLED" (Double L) to match Database/React
        if ("CANCELLED".equals(newStatus) && "CONFIRMED".equals(oldStatus)) {
            System.out.println("✅ Restoring Stock...");
            restoreStock(order);
        }

        // 👉 SCÉNARIO 2 : CONFIRMATION (DEDUCT STOCK)
        else if ("CONFIRMED".equals(newStatus) && !"CONFIRMED".equals(oldStatus)) {
            System.out.println("🔻 Deducting Stock...");
            deductStock(order);
        }

        // Save the new status
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    // --- PRIVATE HELPER METHODS ---

    private void deductStock(Order order) {
        for (OrderItem item : order.getItems()) {
            int qtyToDeduct = item.getQuantity();

            // CAS A : C'est une OPTION
            if (item.getOptionId() != null) {
                Option option = optionRepository.findById(item.getOptionId())
                        .orElseThrow(() -> new RuntimeException("Option not found ID: " + item.getOptionId()));

                int currentStock = option.getStockQuantity() != null ? option.getStockQuantity() : 0;

                if (currentStock < qtyToDeduct) {
                    throw new RuntimeException("Stock insuffisant pour l'option : " + option.getName());
                }

                option.setStockQuantity(currentStock - qtyToDeduct);
                optionRepository.save(option);
            }
            // CAS B : C'est un SUPPLY simple
            else {
                Supply supply = supplyRepository.findById(item.getSupply().getId())
                        .orElseThrow(() -> new RuntimeException("Supply not found"));

                int currentStock = supply.getStockQuantity() != null ? supply.getStockQuantity() : 0;

                if (currentStock < qtyToDeduct) {
                    throw new RuntimeException("Stock insuffisant pour le produit : " + supply.getName());
                }

                supply.setStockQuantity(currentStock - qtyToDeduct);
                supplyRepository.save(supply);
            }
        }
    }

    private void restoreStock(Order order) {
        for (OrderItem item : order.getItems()) {
            int qtyToRestore = item.getQuantity();

            // CAS A : OPTION
            if (item.getOptionId() != null) {
                Option option = optionRepository.findById(item.getOptionId())
                        .orElseThrow(() -> new RuntimeException("Option not found"));

                int current = option.getStockQuantity() != null ? option.getStockQuantity() : 0;
                option.setStockQuantity(current + qtyToRestore);
                optionRepository.save(option);
            }
            // CAS B : SUPPLY
            else {
                Supply supply = supplyRepository.findById(item.getSupply().getId())
                        .orElseThrow(() -> new RuntimeException("Supply not found"));

                int current = supply.getStockQuantity() != null ? supply.getStockQuantity() : 0;
                supply.setStockQuantity(current + qtyToRestore);
                supplyRepository.save(supply);
            }
        }
    }
}