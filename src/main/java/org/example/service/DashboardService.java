package org.example.service;

import org.example.model.DTO.DashboardStatsDTO;
import org.example.model.DTO.DailySalesDTO;
import org.example.model.DTO.TopSchoolDTO;
import org.example.repository.OrderRepository;
import org.example.repository.SchoolRepository;
import org.example.repository.SupplyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final OrderRepository orderRepository;
    private final SchoolRepository schoolRepository;
    private final SupplyRepository supplyRepository;

    public DashboardService(OrderRepository orderRepository, SchoolRepository schoolRepository, SupplyRepository supplyRepository) {
        this.orderRepository = orderRepository;
        this.schoolRepository = schoolRepository;
        this.supplyRepository = supplyRepository;
    }

    /**
     * ✅ METHODE 1: Katjib Ga3 L-KPIs L-Asasiya (FIXED Total Revenue)
     */
    public DashboardStatsDTO getGeneralStats() {

        // 1. Total L-Mabya3at (FIXED: Sta3mel L-Query Bila Waqt - sumTotalAmountByStatus)
        // ⚠️ L-Query calculateTotalRevenue ma-bqaach msta3mal l-L-Total KPI
        // Double revenue = orderRepository.calculateTotalRevenue(startDate, "CONFIRMED"); // L-Qdima

        Double revenue = orderRepository.sumTotalAmountByStatus("CONFIRMED"); // ⬅️ L-Jdida (L-Query katjib L-Majmou3 L-Akkamal)

        // 2. Commandat F-L-Intiḍar (Total L-Orders Pending)
        Long pendingCount = orderRepository.countByStatus("PENDING");

        // 3. L-Madaress L-Moussajjala (Total Count)
        Long schoolCount = schoolRepository.count();

        // 4. Articles F-Khatar L-Nafad (Mital: Supplies li fihom inStock=false)
        Long lowStockCount = supplyRepository.countByInStock(false);

        // 5. L-Sales History l-L-Chart (30 Yawm) - Hada Mazal Khadam b-L-Waqt
        List<DailySalesDTO> salesHistory = getDailySales(30);

        return new DashboardStatsDTO(
                revenue != null ? revenue : 0.0,
                pendingCount != null ? pendingCount : 0L,
                schoolCount != null ? schoolCount : 0L,
                lowStockCount != null ? lowStockCount : 0L,
                salesHistory
        );
    }

    /**
     * ✅ METHODE 2: Katjib L-Mabya3at 7sab L-Yam l-L-Chart (Auxiliaire)
     */
    private List<DailySalesDTO> getDailySales(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);

        List<Object[]> results = orderRepository.findDailySales(startDate);

        return results.stream()
                .map(result -> new DailySalesDTO(
                        ((java.sql.Timestamp) result[0]).toLocalDateTime().toLocalDate(), // Convert to LocalDate
                        (Double) result[1]
                ))
                .sorted((d1, d2) -> d1.getDate().compareTo(d2.getDate()))
                .collect(Collectors.toList());
    }

    /**
     * ✅ METHODE 3 (Jdida): Katjib L-Classement dial L-Madaress 7sab L-Mabya3at.
     */
    public List<TopSchoolDTO> getTopSchools() {

        List<Object[]> results = orderRepository.findTopSchoolsByRevenue();

        return results.stream()
                .map(result -> new TopSchoolDTO(
                        (String) result[0],  // School Name
                        (Long) result[1],    // School ID
                        (Double) result[2],  // Total Revenue (SUM)
                        (Long) result[3]     // Order Count (COUNT)
                ))
                .limit(5)
                .collect(Collectors.toList());
    }
}