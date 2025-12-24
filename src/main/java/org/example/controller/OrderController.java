package org.example.controller;

import org.example.model.DTO.OrderRequestDTO;
import org.example.model.entity.Order;
import org.example.service.OrderService;
import org.example.service.PdfService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map; // 👈 IMPORT ADDED

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final PdfService pdfService;

    public OrderController(OrderService orderService, PdfService pdfService) {
        this.orderService = orderService;
        this.pdfService = pdfService;
    }

    // --- CRUD and Creation ---

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

    // ----------------------------------------------------------------------------------

    /**
     * ✅ UPDATED: Fix for Status Update
     * Accepts JSON: { "status": "CONFIRMED" }
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        try {
            // Extract the "status" value from the JSON body
            String newStatus = payload.get("status");

            // Call the service
            Order updatedOrder = orderService.updateStatus(id, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            // Example: Order not found
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ----------------------------------------------------------------------------------

    // --- PDF GENERATION ENDPOINTS ---

    // 4. Download Client Invoice
    @GetMapping("/{id}/invoice")
    public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        ByteArrayInputStream bis = pdfService.generateInvoice(order);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=facture_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    // 5. Download Preparation Slip
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