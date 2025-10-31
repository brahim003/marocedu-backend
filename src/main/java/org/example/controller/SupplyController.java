package org.example.controller;

import org.example.model.DTO.SupplyDTO;
import org.example.service.SupplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/supplies") // Base URL pour toutes les fournitures
public class SupplyController {

    private final SupplyService supplyService;

    // Injection via Constructeur
    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    // ✅ NOUVEL ENDPOINT POUR LE DÉTAIL PRODUIT : GET /api/supplies/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SupplyDTO> getSupplyById(@PathVariable Long id) {
        try {
            // Appelle le Service (qui trouvera le produit complet avec options/images)
            SupplyDTO supplyDTO = supplyService.getSupplyById(id);
            return ResponseEntity.ok(supplyDTO);
        } catch (RuntimeException e) {
            // Si le Service lance une RuntimeException (produit non trouvé), on retourne 404
            return ResponseEntity.notFound().build();
        }
    }

    // L'ENDPOINT EXISTANT : GET /api/supplies/by-school/{schoolSlug}/level/{levelSlug}
    @GetMapping("/by-school/{schoolSlug}/level/{levelSlug}")
    public ResponseEntity<List<SupplyDTO>> getSuppliesBySchoolAndLevel(
            @PathVariable String schoolSlug,
            @PathVariable String levelSlug) {

        List<SupplyDTO> supplies = supplyService.getSuppliesBySchoolAndLevel(schoolSlug, levelSlug);

        // Si la liste est vide, on renvoie 200 OK avec un corps vide.
        return ResponseEntity.ok(supplies);
    }
}