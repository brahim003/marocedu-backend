package org.example.model.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // ✅ 1. ZEDNA DESCRIPTION (Darori bach Service mayb9ax hmar)
    @Column(length = 1000) // Dernaha kbira bach thaz description twila
    private String description;

    private Double price;
    private String currency;
    private Boolean inStock;
    private String marque;
    private String position;

    // ✅ ZEDNA ISBN HNA
    private String isbn;

    // ✅ 2. ZEDNA IMAGE (Bach nkhabiw smiyt tswira)
    private String image;

    @Column(name = "is_book")
    private Boolean isBook;

    // Many supplies can belong to one level
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    // ✅ 3. ZEDNA SCHOOL (Bach nrabto sl3a b madrasa)
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    // One supply → Many options
    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private List<Option> options;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;


    // --- Constructeur Vide (Darori l JPA) ---
    public Supply() {}

    // --- Constructeur Complet (Updated) ---
    public Supply(String name, String description, Double price, String currency, Boolean inStock,
                  String marque, String position, String isbn, String image,
                  Boolean isBook, Level level, School school, List<Option> options) {
        this.name = name;
        this.description = description; // ✅ Added
        this.price = price;
        this.currency = currency;
        this.inStock = inStock;
        this.marque = marque;
        this.position = position;
        this.isbn = isbn;
        this.image = image; // ✅ Added
        this.isBook = isBook;
        this.level = level;
        this.school = school; // ✅ Added
        this.options = options;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // ✅ Getters/Setters Description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

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

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    // ✅ Getters/Setters Image
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Boolean getIsBook() { return isBook != null ? isBook : false; }
    public void setIsBook(Boolean isBook) { this.isBook = isBook; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    // ✅ Getters/Setters School
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }
}