package org.example.model.entity;

import jakarta.persistence.Entity;

import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School school;           // Belongs to one school

    @ManyToMany
    @JoinTable(
            name = "level_supply",                  // join table name
            joinColumns = @JoinColumn(name = "level_id"),   // column for Level
            inverseJoinColumns = @JoinColumn(name = "supply_id") // column for Supply
    )
    private List<Supply> supplies;   // One level → Many supplies

    public Level() {}

    public Level(Long id, String name, String slug, School school, List<Supply> supplies) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.school = school;
        this.supplies = supplies;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public List<Supply> getSupplies() { return supplies; }
    public void setSupplies(List<Supply> supplies) { this.supplies = supplies; }
}
