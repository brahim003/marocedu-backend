package org.example.model.entity;

import jakarta.persistence.*;
// import java.util.List; // Cette ligne n'est pas utilisée

@Entity
@Table(name = "options") // C'est une bonne pratique de nommer la table
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String name; // Le champ est ici

    private Double price;

    private String currency;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply; // Belongs to one supply

    // Constructeur vide (obligatoire pour JPA)
    public Option() {}

    // Constructeur complet (mis à jour)
    public Option(String image, String name, Double price, String currency, Supply supply) {
        this.image = image;
        this.name = name; // Ajouté ici
        this.price = price;
        this.currency = currency;
        this.supply = supply;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    // C'EST LA PARTIE QUI MANQUAIT PROBABLEMENT
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Supply getSupply() { return supply; }
    public void setSupply(Supply supply) { this.supply = supply; }
}