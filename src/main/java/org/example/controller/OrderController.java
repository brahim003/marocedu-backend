package org.example.controller;

import org.example.model.DTO.OrderRequestDTO;
import org.example.model.entity.Order;
import org.example.service.OrderService;
import org.example.service.PdfService; // 💡 NEW: Import PdfService
import org.springframework.core.io.InputStreamResource; // For file streaming
import org.springframework.http.HttpHeaders; // For file headers
import org.springframework.http.MediaType; // For PDF content type
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream; // For stream handling
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final PdfService pdfService; // ✅ NEW: Declare PDF service

    public OrderController(OrderService orderService, PdfService pdfService) { // ✅ UPDATE: Inject PDF service
        this.orderService = orderService;
        this.pdfService = pdfService;
    }

    // --- CRUD and Creation (Existing methods) ---

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        try {
            orderService.createOrder(orderRequest);
            return ResponseEntity.ok("Commande enregistrée avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erreur lors de la commande: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // --- PDF GENERATION ENDPOINTS (NEW) ---

    // 4. Télécharger Facture Client (GET /api/orders/{id}/invoice)
    @GetMapping("/{id}/invoice")
    public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        ByteArrayInputStream bis = pdfService.generateInvoice(order);

        HttpHeaders headers = new HttpHeaders();
        // Set 'inline' to view in browser, 'attachment' to force download
        headers.add("Content-Disposition", "inline; filename=facture_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    // 5. Télécharger Bon de Préparation Magasin (GET /api/orders/{id}/preparation)
    @GetMapping("/{id}/preparation")
    public ResponseEntity<InputStreamResource> downloadPreparation(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        ByteArrayInputStream bis = pdfService.generatePreparationSlip(order);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=bon_prepa_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}