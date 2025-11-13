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
    private String position;

    // ✅ ZEDNA ISBN HNA (Jdid)
    private String isbn;

    // ✅ FIX CRITIQUE : On fait le lien explicite avec la colonne "is_book"
    @Column(name = "is_book")
    private Boolean isBook;

    // Many supplies can belong to one level
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    // One supply → Many options
    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private List<Option> options;

    public Supply() {}

    // Constructeur complet (M-update b ISBN)
    public Supply(String name, Double price, String currency, Boolean inStock,
                  String marque, String position,
                  String isbn, // 👈 Ajouté ici dans le constructeur
                  Boolean isBook,
                  Level level, List<Option> options) {
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.inStock = inStock;
        this.marque = marque;
        this.position = position;
        this.isbn = isbn; // 👈 Initialisation
        this.isBook = isBook;
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

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    // ✅ Getter & Setter ISBN
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    // ✅ GETTER SÉCURISÉ pour isBook
    public Boolean getIsBook() {
        return isBook != null ? isBook : false;
    }

    public void setIsBook(Boolean isBook) {
        this.isBook = isBook;
    }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }
}