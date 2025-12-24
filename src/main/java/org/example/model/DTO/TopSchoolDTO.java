package org.example.model.DTO;

public class TopSchoolDTO {

    private String schoolName;
    private Long schoolId; // ID dial L-Madrassa
    private Double totalRevenue;
    private Long orderCount; // 3adad L-Commandat
    // N9addu n-zidou L-Top Level L-li khdam m3aha

    public TopSchoolDTO() {}

    public TopSchoolDTO(String schoolName, Long schoolId, Double totalRevenue, Long orderCount) {
        this.schoolName = schoolName;
        this.schoolId = schoolId;
        this.totalRevenue = totalRevenue;
        this.orderCount = orderCount;
    }

    // --- Getters & Setters ---

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

    // Taṣḥiḥ L-Setters (Makhasshach Tr-Radd return)
    public Long getSchoolId() { return schoolId; }
    public void setSchoolId(Long schoolId) { this.schoolId = schoolId; } // ⬅️ Saḥiḥ

    public Double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Double totalRevenue) { this.totalRevenue = totalRevenue; } // ⬅️ Saḥiḥ

    public Long getOrderCount() { return orderCount; }
    public void setOrderCount(Long orderCount) { this.orderCount = orderCount; } // ⬅️ Saḥiḥ
}