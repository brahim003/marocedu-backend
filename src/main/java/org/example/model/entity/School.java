package org.example.model.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Level> levels; // One school → Many levels
    @Column(name = "logo_path") // Hada howa smit l-colonne f database
    private String logo; // Hada howa smit l-attribut f Java
    private String city;

    public School() {}

    // Corrected Constructor
    public School(Long id, String name, String slug, List<Level> levels, String logo, String city) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.levels = levels;
        this.logo = logo; // Correct
        this.city = city; // Correct
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public List<Level> getLevels() { return levels; }
    public void setLevels(List<Level> levels) { this.levels = levels; }

    public String getLogo() {return logo;}
    public void setLogo(String logo) {this.logo = logo;}

    public String getCity(){return city;}
    public void setCity(String city){this.city=city;}

}
