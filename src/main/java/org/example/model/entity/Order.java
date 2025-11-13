package org.example.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shop_orders") // ⚠️ "orders" est un mot réservé en SQL, on utilise "shop_orders"
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- 1. INFO CLIENT (Guest ou User) ---
    private String customerName;
    private String customerPhone;
    private String deliveryAddress;
    private String notes; // Pour "Sonnez à l'interphone..."

    // --- 2. INFO PAIEMENT ---
    private Double totalAmount;        // Total à payer
    private Boolean packagingRequested; // Est-ce qu'il a coché l'emballage ?
    private LocalDateTime orderDate;
    private String status;             // EX: "PENDING", "CONFIRMED"

    // --- 3. RELATIONS ---

    // Lien optionnel avec un User (nullable = true permet aux invités de commander)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    // Lien avec les articles commandés (On utilise OrderItem, pas CartItem)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    // --- CONSTRUCTEURS ---

    public Order() {
        this.orderDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Helper pour ajouter facilement un article à la liste
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Boolean getPackagingRequested() { return packagingRequested; }
    public void setPackagingRequested(Boolean packagingRequested) { this.packagingRequested = packagingRequested; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}