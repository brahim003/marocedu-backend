package org.example.model.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply; // One cart item → One supply

    private Integer quantity;
    private Double price;   // Calculated as supply price * quantity

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;    // Belongs to one order

    public CartItem() {}

    public CartItem(Long id, Supply supply, Integer quantity, Double price, Order order) {
        this.id = id;
        this.supply = supply;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Supply getSupply() { return supply; }
    public void setSupply(Supply supply) { this.supply = supply; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
