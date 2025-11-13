package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // ✅ 1. IMPORT DAROURI
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    // 🛑 HNA FIN KAN-9ET3O L-BOUCLE 🛑
    // "Mlli t-affichi l-Image, mat-jibch m3aha l-Option parent."
    @JsonIgnore // ✅ 2. ANNOTATION DAROURIYA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    public Image() {}

    public Image(String path, Option option) {
        this.path = path;
        this.option = option;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public Option getOption() { return option; }
    public void setOption(Option option) { this.option = option; }
}