package org.example.model.DTO;

import java.time.LocalDate;

public class DailySalesDTO {

    private LocalDate date;
    private Double amount;

    public DailySalesDTO() {}

    public DailySalesDTO(LocalDate date, Double amount) {
        this.date = date;
        this.amount = amount;
    }

    // --- Getters & Setters ---

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}