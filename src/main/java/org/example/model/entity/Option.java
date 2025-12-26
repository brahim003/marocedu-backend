package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // ✅ 1. IMPORT ESSENTIEL
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private Double price;
    private String currency;
    private String marque;
    private String position;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    // 🛑 STOP LA BOUCLE INFINIE ICI 🛑
    // On dit à Java : "Quand tu envoies l'Option au Frontend, n'envoie pas tout le Supply parent avec."
    @JsonIgnore // ✅ 2. LIGNE MAGIQUE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @OneToMany(
            mappedBy = "option",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Image> images = new ArrayList<>();

    public Option() {}

    public Option(String image, String name, Double price, String currency,
                  String marque, String position,
                  Supply supply) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.marque = marque;
        this.position = position;
        this.supply = supply;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Supply getSupply() { return supply; }
    public void setSupply(Supply supply) { this.supply = supply; }

    public List<Image> getImages() { return images; }
    public void setImages(List<Image> images) { this.images = images; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
}