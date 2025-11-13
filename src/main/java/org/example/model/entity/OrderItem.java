package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // 👈 1. IMPORT CRUCIAL
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private Long optionId;

    // Position f stock
    private String position;

    // ISBN pour le bon de commande
    private String isbn;

    // --- RELATIONSHIPS ---

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

    // 🛑 STOP LA BOUCLE INFINIE ICI 🛑
    @JsonIgnore // 👈 2. ANNOTATION MAGIQUE
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem() {}

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Long getOptionId() { return optionId; }
    public void setOptionId(Long optionId) { this.optionId = optionId; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Supply getSupply() { return supply; }
    public void setSupply(Supply supply) { this.supply = supply; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}