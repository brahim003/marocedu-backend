package org.example.model.DTO;

import java.util.List;

public class SupplyDTO {

    private Long id;
    private String name;
    private Double price;
    private String currency;
    private Boolean inStock;
    private String marque;
    private String position;

    // ✅ الحقل اللي كان ناقص ومسببل ليك Error
    private String image;

    private Boolean isBook;
    private String school;
    private String level;
    private List<OptionDTO> options;

    // --- Getters & Setters ---

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

    // ✅ Getters & Setters ديال Image
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Boolean getIsBook() { return isBook; }
    public void setIsBook(Boolean isBook) { this.isBook = isBook; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public List<OptionDTO> getOptions() { return options; }
    public void setOptions(List<OptionDTO> options) { this.options = options; }
}