package org.example.model.DTO;

public class OrderItemRequestDTO {
    private Long supplyId; // L-ID dyal l-produit (Li jay mn React)
    private int quantity;
    private Long optionId; // L-ID dyal l-couleur/option

    public OrderItemRequestDTO() {}

    // Getters & Setters
    public Long getSupplyId() { return supplyId; }
    public void setSupplyId(Long supplyId) { this.supplyId = supplyId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Long getOptionId() { return optionId; }
    public void setOptionId(Long optionId) { this.optionId = optionId; }
}