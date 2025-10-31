package org.example.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images") // Hada howa s-smia dial la table f BDD
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Le chemin d'accès au fichier (ex: /images/compas/bleu_face.jpeg)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id") // La clé étrangère li katrbothom
    private Option option;

    public Image() {}

    public Image(String path, Option option) {
        this.path = path;
        this.option = option;
    }


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

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}