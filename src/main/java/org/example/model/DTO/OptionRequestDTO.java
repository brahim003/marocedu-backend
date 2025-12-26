package org.example.model.DTO;

public class OptionRequestDTO {
    private String optionName;     // مثال: "Taille S"
    private Integer stockQuantity; // مثال: 50

    // --- Getters & Setters ---
    public String getOptionName() { return optionName; }
    public void setOptionName(String optionName) { this.optionName = optionName; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
}