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

    // Many supplies can belong to one level
    @ManyToOne
    @JoinColumn(name = "level_id") // foreign key column in Supply table
    private Level level;

    // One supply → Many options
    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private List<Option> options;

    public Supply() {}

    public Supply(Long id, String name, Double price, String currency, Boolean inStock, Level level, List<Option> options) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.inStock = inStock;
        this.level = level;
        this.options = options;
    }

    // getters & setters
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

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }
}
