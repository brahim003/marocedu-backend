package org.example.repository;

import org.example.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 1. 3adad L-Commandat 7sab L-7ala
    Long countByStatus(String status);

    // 2. ✅ TAṢḤIḤ L-KPI: Total L-Mabya3at L-Mkaffla (Bila Waqt)
    // Hada howa L-L-Query L-Akkamal li ghadi t-koun mawjouda.
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = :status")
    Double sumTotalAmountByStatus(@Param("status") String status);

    // (L-Method L-Qdima dial calculateTotalRevenue 7iyydnah mn hna bach maykounch L-khalat)

    // 3. L-Mabya3at L-Yawmiya l-L-Chart (Sales History)
    @Query("SELECT FUNCTION('DATE', o.orderDate), SUM(o.totalAmount) " +
            "FROM Order o " +
            "WHERE o.status = 'CONFIRMED' AND o.orderDate >= :startDate " +
            "GROUP BY FUNCTION('DATE', o.orderDate) " +
            "ORDER BY FUNCTION('DATE', o.orderDate) ASC")
    List<Object[]> findDailySales(@Param("startDate") LocalDateTime startDate);

    // 4. ✅ Tahlil L-Mabya3at 7sab L-Madrassa (Top Schools)
    @Query("SELECT s.name, s.id, SUM(o.totalAmount), COUNT(o.id) " +
            "FROM Order o " +
            "JOIN o.items oi " +
            "JOIN oi.supply sup " +
            "JOIN sup.level l " +
            "JOIN l.school s " +
            "WHERE o.status = 'CONFIRMED' " +
            "GROUP BY s.name, s.id " +
            "ORDER BY SUM(o.totalAmount) DESC")
    List<Object[]> findTopSchoolsByRevenue();
}