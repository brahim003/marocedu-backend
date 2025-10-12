package org.example.model.entity;

import java.util.List;

public class Level {
    private Long id;
    private String name;
    private String slug;
    private School school;           // Belongs to one school
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
