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

    public School() {}

    public School(Long id, String name, String slug, List<Level> levels) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.levels = levels;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public List<Level> getLevels() { return levels; }
    public void setLevels(List<Level> levels) { this.levels = levels; }
}
