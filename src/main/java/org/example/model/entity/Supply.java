package org.example.model.entity;

import java.util.List;

public class Supply {
    private Long id;
    private String name;
    private Double price;
    private String currency;
    private Boolean inStock;
    private Level level;             // Belongs to one level
    private List<Option> options;    // One supply → Many options

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
