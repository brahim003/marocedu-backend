package org.example.model.DTO;

import java.util.List; //  Importation nécessaire

public class OptionDTO {
    private Long id;
    private String image;
    private String name;
    private Double price;
    private String currency;
    private String marque;
    private String position;

    //  NOUVEAU CHAMP : Liste des photos (angles multiples)
    private List<ImageDTO> images;

    // --- Getters & Setters ---

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

    // Getters et Setters pour marque
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    // Getters et Setters pour position
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    //  Getters et Setters pour la liste des images
    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }
}