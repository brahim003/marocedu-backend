package org.example.model.DTO; // Assurez-vous que le package est correct

public class ImageDTO {

    private Long id;
    private String path; // Le chemin d'accès à la photo

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}