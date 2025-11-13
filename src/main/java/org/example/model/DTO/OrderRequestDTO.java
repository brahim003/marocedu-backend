package org.example.model.DTO;

import java.util.List;

public class OrderRequestDTO {
    // Infos Client
    private String customerName;
    private String customerPhone;
    private String deliveryAddress;
    private String notes;

    // Infos Paiement
    private Boolean packagingRequested;
    private Double totalAmount;

    // La Liste des articles
    private List<OrderItemRequestDTO> items;

    public OrderRequestDTO() {}

    // Getters & Setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Boolean getPackagingRequested() { return packagingRequested; }
    public void setPackagingRequested(Boolean packagingRequested) { this.packagingRequested = packagingRequested; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public List<OrderItemRequestDTO> getItems() { return items; }
    public void setItems(List<OrderItemRequestDTO> items) { this.items = items; }
}