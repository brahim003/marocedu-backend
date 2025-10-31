package org.example.model.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String currency;
    private Boolean inStock;
    private String marque;
    private String position; // (Location in stock, e.g., "A6")


    // Many supplies can belong to one level
    @ManyToOne
    @JoinColumn(name = "level_id") // foreign key column in Supply table
    private Level level;

    // One supply → Many options
    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private List<Option> options;

    public Supply() {}

    // Constructeur m-modifi bach ydkhel fih les champs jdad
    public Supply(String name, Double price, String currency, Boolean inStock,
                  String marque, String position, // Hna zednahom
                  Level level, List<Option> options) {
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.inStock = inStock;
        this.marque = marque; // Hna zednahom
        this.position = position; // Hna zednahom
        this.level = level;
        this.options = options;
    }

    // --- getters & setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Boolean getInStock() { return inStock; }
    public void setInStock(Boolean inStock) { this.inStock = inStock; }

    // --- ✅ Getters/Setters Jdad ---
    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    // --- Fin dial Getters/Setters Jdad ---

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }
}