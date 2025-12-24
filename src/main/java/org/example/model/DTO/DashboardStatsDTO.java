package org.example.model.DTO;

import java.util.List;

public class DashboardStatsDTO {

    private Double totalRevenue;
    private Long pendingOrdersCount;
    private Long schoolCount;
    private Long lowStockItemsCount;

    // L-List dial DailySales l-L-Graphique
    private List<DailySalesDTO> salesHistory;

    public DashboardStatsDTO() {}

    public DashboardStatsDTO(Double totalRevenue, Long pendingOrdersCount, Long schoolCount, Long lowStockItemsCount, List<DailySalesDTO> salesHistory) {
        this.totalRevenue = totalRevenue;
        this.pendingOrdersCount = pendingOrdersCount;
        this.schoolCount = schoolCount;
        this.lowStockItemsCount = lowStockItemsCount;
        this.salesHistory = salesHistory;
    }

    // --- Getters & Setters ---

    public Double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Double totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getPendingOrdersCount() { return pendingOrdersCount; }
    public void setPendingOrdersCount(Long pendingOrdersCount) { this.pendingOrdersCount = pendingOrdersCount; }

    public Long getSchoolCount() { return schoolCount; }
    public void setSchoolCount(Long schoolCount) { this.schoolCount = schoolCount; }

    public Long getLowStockItemsCount() { return lowStockItemsCount; }
    public void setLowStockItemsCount(Long lowStockItemsCount) { this.lowStockItemsCount = lowStockItemsCount; }

    public List<DailySalesDTO> getSalesHistory() { return salesHistory; }
    public void setSalesHistory(List<DailySalesDTO> salesHistory) { this.salesHistory = salesHistory; }
}