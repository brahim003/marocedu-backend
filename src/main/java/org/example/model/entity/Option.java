package org.example.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList; // ✅ Import
import java.util.List; // ✅ Import

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private Double price;
    private String currency;
    private String marque;
    private String position;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_id")
    private Supply supply;

    // ✅ NOUVELLE RELATION : One-to-Many vers la liste des Images
    @OneToMany(
            mappedBy = "option",
            cascade = CascadeType.ALL, // Ila m7iti option, ytme7aw les images dialha
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Image> images = new ArrayList<>(); // ✅ Nouvelle liste

    public Option() {}

    // Constructeur complet (non mis à jour pour inclure les images pour la simplicité)
    public Option(String image, String name, Double price, String currency,
                  String marque, String position,
                  Supply supply) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.marque = marque;
        this.position = position;
        this.supply = supply;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }


    public Supply getSupply() { return supply; }
    public void setSupply(Supply supply) { this.supply = supply; }

    // ✅ NOUVEAUX GETTERS/SETTERS
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}