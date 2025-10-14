package org.example.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private Double price;
    private String currency;
    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply; // Belongs to one supply

    public Option() {}

    public Option(Long id, String image, Double price, String currency, Supply supply) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.currency = currency;
        this.supply = supply;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Supply getSupply() { return supply; }
    public void setSupply(Supply supply) { this.supply = supply; }
}
