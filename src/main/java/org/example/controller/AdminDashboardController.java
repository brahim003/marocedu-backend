package org.example.controller;

import org.example.model.DTO.DashboardStatsDTO;
import org.example.model.DTO.TopSchoolDTO; // ⬅️ NOUVEAU IMPORT
import org.example.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controller L-Khas b-L-Iḥsa'iyat w L-Ma3lumat dial L-Admin Dashboard.
 */
@RestController
@RequestMapping("/api/admin/dashboard") // L-Prefix dial L-Routes dial L-Admin Dashboard
public class AdminDashboardController {

    private final DashboardService dashboardService;

    // Injection via Constructeur
    public AdminDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * ✅ METHODE 1: Katjib Ga3 L-Iḥsa'iyat L-Mujamma3a (KPIs w Sales History)
     * URL: GET /api/admin/dashboard/stats
     * L-Réponse: DashboardStatsDTO
     */
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats() {
        DashboardStatsDTO stats = dashboardService.getGeneralStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * ✅ METHODE 2 (Jdida): Katjib L-Classement dial L-Madaress L-Afdal.
     * URL: GET /api/admin/dashboard/top-schools
     * L-Réponse: List<TopSchoolDTO>
     */
    @GetMapping("/top-schools")
    public ResponseEntity<List<TopSchoolDTO>> getTopSchools() {
        // Kan3aytou 3la L-Service bach y-jib L-List dial Top Schools
        List<TopSchoolDTO> topSchools = dashboardService.getTopSchools();

        // Katr-radd L-Réponse (200 OK) m3a L-List
        return ResponseEntity.ok(topSchools);
    }
}