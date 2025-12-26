package org.example.model.DTO;

import java.util.List;

public class SupplyRequestDTO {
    private String name;
    private String description;
    private Double price;

    // ✅ الحقول الإضافية (باش ما يبقاوش null)
    private String isbn;
    private String marque;
    private String position;
    private String currency = "MAD"; // Default
    private Boolean isBook;
    private Integer stockQuantity; // للسلعة اللي بلا options

    // ✅ المفاتيح (Ids) - ضروريين دابا
    private Long levelId;
    private Long schoolId;

    // الليست ديال الخيارات
    private List<OptionRequestDTO> options;

    // --- Getters & Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Boolean getIsBook() { return isBook; }
    public void setIsBook(Boolean isBook) { this.isBook = isBook; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Long getLevelId() { return levelId; }
    public void setLevelId(Long levelId) { this.levelId = levelId; }

    public Long getSchoolId() { return schoolId; }
    public void setSchoolId(Long schoolId) { this.schoolId = schoolId; }

    public List<OptionRequestDTO> getOptions() { return options; }
    public void setOptions(List<OptionRequestDTO> options) { this.options = options; }
}