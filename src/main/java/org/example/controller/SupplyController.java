package org.example.controller;

import org.example.model.DTO.SupplyDTO;
import org.example.model.DTO.SupplyRequestDTO; // ✅ Import جديد لـ DTO ديال الإدخال
import org.example.model.entity.Supply;       // ✅ Import جديد لـ Entity
import org.example.service.SupplyService;
import org.springframework.http.MediaType;      // ✅ ضروري باش نعرفو نوع البيانات (Multipart)
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // ✅ Import ديال الفيشي (الصورة)

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/supplies") // Base URL pour toutes les fournitures
@CrossOrigin("*") // ✅ مهم جداً: باش React يقدر يهضر مع الباكند بلا مشاكل
public class SupplyController {

    private final SupplyService supplyService;

    // Injection via Constructeur
    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    // =================================================================
    // 🆕 1. PARTIE ÉCRITURE : AJOUTER UNE NOUVELLE FOURNITURE (POST)
    // =================================================================

    // هذا هو الـ Endpoint الجديد اللي كيستقبل JSON (data) و Fichier (image)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Supply> createSupply(
            // 1. استقبال البيانات (السمية، الثمن، المدرسة، الخيارات...)
            @RequestPart("data") SupplyRequestDTO request,

            // 2. استقبال التصويرة (اختياري)
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        // نعيطو للخدمة الجديدة فـ Service
        Supply newSupply = supplyService.createSupplyWithImage(request, image);

        return ResponseEntity.ok(newSupply);
    }

    // =================================================================
    // 📖 2. PARTIE LECTURE (GET) - الكود القديم ديالك كما هو
    // =================================================================

    // ✅ ENDPOINT POUR LE DÉTAIL PRODUIT : GET /api/supplies/{id}
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

    // =================================================================
    // ⚙️ 3. PARTIE ADMIN (GESTION) - Endpoints جداد
    // =================================================================

    // ✅ باش نجيبو السلع ديال مستوى محدد بـ ID (Admin Panel)
    @GetMapping("/level/{levelId}")
    public ResponseEntity<List<SupplyDTO>> getSuppliesByLevelId(@PathVariable Long levelId) {
        // تأكد أن SupplyService فيه دالة سميتها getSuppliesByLevelId(Long id)
        List<SupplyDTO> supplies = supplyService.getSuppliesByLevelId(levelId);
        return ResponseEntity.ok(supplies);
    }

    // ✅ باش نمسحو سلعة (Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.noContent().build();
    }


}