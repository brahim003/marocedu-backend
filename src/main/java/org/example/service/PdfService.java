package org.example.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.example.model.entity.Order;
import org.example.model.entity.OrderItem;
import org.example.model.entity.Option;
import org.example.model.entity.Supply;
import org.example.model.entity.Level;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfService {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final double DELIVERY_FEE = 8.00;
    private static final double PACKAGING_PRICE = 5.00;

    // Helper pour extraire l'École et le Niveau (du premier article)
    private String getSchoolLevelInfo(Order order) {
        if (order.getItems().isEmpty()) return "";
        try {
            Level level = order.getItems().get(0).getSupply().getLevel();
            if (level == null) return "";
            String schoolName = level.getSchool().getName();
            String levelName = level.getName();
            return "École : " + schoolName + " / Niveau : " + levelName;
        } catch (Exception e) {
            return "École/Niveau : Données non spécifiées.";
        }
    }

    // --- 1. GÉNÉRER LA FACTURE CLIENT (Avec Prix) ---
    public ByteArrayInputStream generateInvoice(Order order) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // HEADER & INFO CLIENT
            addHeader(document, "FACTURE CLIENT", order);

            // ✅ AJOUT DE L'ÉCOLE ET DU NIVEAU (Facture)
            String schoolLevel = getSchoolLevelInfo(order);
            if (!schoolLevel.isEmpty()) {
                document.add(new Paragraph(schoolLevel, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK)));
                document.add(Chunk.NEWLINE);
            }

            // TABLEAU DES PRODUITS
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 1, 2, 2});

            addTableHeader(table, "Description");
            addTableHeader(table, "Qté");
            addTableHeader(table, "Prix Unit.");
            addTableHeader(table, "Total");

            double itemsTotal = 0.0;
            int totalBooks = 0;

            for (OrderItem item : order.getItems()) {
                Supply supply = item.getSupply();
                String name = supply.getName();
                Double price = supply.getPrice();

                if (Boolean.TRUE.equals(supply.getIsBook())) {
                    totalBooks += item.getQuantity();
                }

                if (item.getOptionId() != null) {
                    Option selectedOption = supply.getOptions().stream()
                            .filter(opt -> opt.getId().equals(item.getOptionId()))
                            .findFirst()
                            .orElse(null);

                    if (selectedOption != null) {
                        price = selectedOption.getPrice();
                        name += " (" + selectedOption.getName() + ")";
                    }
                }

                double lineTotal = price * item.getQuantity();
                itemsTotal += lineTotal;

                addCell(table, name, false);
                addCell(table, String.valueOf(item.getQuantity()), false);
                addCell(table, df.format(price) + " DH", false);
                addCell(table, df.format(lineTotal) + " DH", false);
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            // TOTAUX
            addTotals(document, order, itemsTotal, totalBooks);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // --- 2. GÉNÉRER LE BON DE PRÉPARATION (Sans Prix) ---
    public ByteArrayInputStream generatePreparationSlip(Order order) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // EN-TÊTE DU BON DE PRÉPARATION
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.DARK_GRAY);
            Paragraph title = new Paragraph("BON DE PRÉPARATION COMMANDE #" + order.getId(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // ✅ INFO ÉCOLE
            String schoolLevel = getSchoolLevelInfo(order);
            if (!schoolLevel.isEmpty()) {
                Paragraph pSchool = new Paragraph(schoolLevel, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLACK));
                pSchool.setAlignment(Element.ALIGN_CENTER);
                document.add(pSchool);
                document.add(Chunk.NEWLINE);
            }

            // INFO CLIENT
            document.add(new Paragraph("Client: " + order.getCustomerName()));
            document.add(new Paragraph("Tél: " + order.getCustomerPhone()));
            document.add(new Paragraph("Adresse: " + order.getDeliveryAddress()));

            // 🔥🔥🔥 AJOUT: NOTES CLIENT (Important pour le préparateur) 🔥🔥🔥
            if (order.getNotes() != null && !order.getNotes().trim().isEmpty()) {
                document.add(Chunk.NEWLINE);

                // Titre rouge
                Font noteTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.RED);
                document.add(new Paragraph("⚠️ REMARQUE CLIENT :", noteTitleFont));

                // Tableau jaune pour mettre en valeur la note
                PdfPTable noteTable = new PdfPTable(1);
                noteTable.setWidthPercentage(100);
                noteTable.setSpacingBefore(5);

                PdfPCell noteCell = new PdfPCell(new Phrase(order.getNotes(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
                noteCell.setBackgroundColor(new Color(255, 255, 224)); // Light Yellow
                noteCell.setPadding(8);
                noteCell.setBorderColor(Color.ORANGE);

                noteTable.addCell(noteCell);
                document.add(noteTable);
            }
            // 🔥🔥🔥 FIN AJOUT NOTES 🔥🔥🔥

            document.add(Chunk.NEWLINE);

            // TABLEAU (Trié par Position)
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 4, 1});

            // Headers avec fond gris
            PdfPCell h1 = new PdfPCell(new Phrase("[ ]", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
            h1.setBackgroundColor(Color.GRAY);
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(h1);

            PdfPCell h2 = new PdfPCell(new Phrase("POS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
            h2.setBackgroundColor(Color.GRAY);
            table.addCell(h2);

            PdfPCell h3 = new PdfPCell(new Phrase("Article & Option", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
            h3.setBackgroundColor(Color.GRAY);
            table.addCell(h3);

            PdfPCell h4 = new PdfPCell(new Phrase("QTÉ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
            h4.setBackgroundColor(Color.GRAY);
            h4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(h4);

            // Tri par Position
            List<OrderItem> sortedItems = order.getItems().stream()
                    .sorted(Comparator.comparing(i -> i.getPosition() != null ? i.getPosition() : "ZZZ"))
                    .collect(Collectors.toList());

            for (OrderItem item : sortedItems) {
                String name = item.getSupply().getName();

                if (item.getOptionId() != null) {
                    Option opt = item.getSupply().getOptions().stream()
                            .filter(o -> o.getId().equals(item.getOptionId()))
                            .findFirst().orElse(null);
                    if (opt != null) name += " - " + opt.getName();
                }

                // Cellule Checkbox vide
                PdfPCell checkCell = new PdfPCell(new Phrase("___"));
                checkCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                checkCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(checkCell);

                // Cellule Position
                String pos = item.getPosition() != null ? item.getPosition() : "??";
                table.addCell(new Phrase(pos, FontFactory.getFont(FontFactory.HELVETICA, 10)));

                // Cellule Nom
                table.addCell(new Phrase(name, FontFactory.getFont(FontFactory.HELVETICA, 10)));

                // Cellule Quantité (Gras et Centré)
                PdfPCell qtyCell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity()), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                qtyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                qtyCell.setPadding(5);
                table.addCell(qtyCell);
            }

            document.add(table);

            // Info Emballage pour le préparateur
            if(Boolean.TRUE.equals(order.getPackagingRequested())) {
                document.add(Chunk.NEWLINE);
                Font packFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE);
                Paragraph packPara = new Paragraph("⚠️ ATTENTION : EMBALLAGE DEMANDÉ", packFont);
                packPara.setAlignment(Element.ALIGN_CENTER);
                document.add(packPara);
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    // --- Helpers pour l'Invoice ---

    private void addHeader(Document doc, String titleStr, Order order) {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
        Paragraph title = new Paragraph(titleStr + " #" + order.getId(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        try {
            doc.add(title);
            doc.add(Chunk.NEWLINE);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            doc.add(new Paragraph("Client : " + order.getCustomerName()));
            doc.add(new Paragraph("Tél : " + order.getCustomerPhone()));
            doc.add(new Paragraph("Adresse : " + order.getDeliveryAddress()));
            doc.add(new Paragraph("Date : " + sdf.format(java.sql.Timestamp.valueOf(order.getOrderDate()))));
            doc.add(Chunk.NEWLINE);
        } catch (DocumentException e) { e.printStackTrace(); }
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(Color.GRAY);
        header.setPhrase(new Phrase(headerTitle, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE)));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setPadding(6);
        table.addCell(header);
    }

    private void addCell(PdfPTable table, String text, boolean isHeader) {
        Font font = isHeader ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE) : FontFactory.getFont(FontFactory.HELVETICA, 10);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        if (isHeader) cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }

    private void addTotals(Document doc, Order order, double itemsTotal, int totalBooks) throws DocumentException {
        // Sous-Total Articles
        Paragraph pItems = new Paragraph("Sous-total articles : " + df.format(itemsTotal) + " DH");
        pItems.setAlignment(Element.ALIGN_RIGHT);
        doc.add(pItems);

        // Frais Livraison
        Paragraph pDeliv = new Paragraph("Livraison : " + df.format(DELIVERY_FEE) + " DH");
        pDeliv.setAlignment(Element.ALIGN_RIGHT);
        doc.add(pDeliv);

        // Emballage (Calculé)
        if (Boolean.TRUE.equals(order.getPackagingRequested())) {
            double packagingCost = totalBooks * PACKAGING_PRICE;
            Paragraph pPack = new Paragraph("Emballage (" + totalBooks + " livres) : " + df.format(packagingCost) + " DH");
            pPack.setAlignment(Element.ALIGN_RIGHT);
            doc.add(pPack);
        }

        doc.add(Chunk.NEWLINE);

        // GRAND TOTAL FINAL
        Paragraph pTotal = new Paragraph("TOTAL À PAYER : " + df.format(order.getTotalAmount()) + " DH",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLUE));
        pTotal.setAlignment(Element.ALIGN_RIGHT);
        doc.add(pTotal);
    }
}