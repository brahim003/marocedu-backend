package org.example.model.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;
    private String cycle;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School school;

    @ManyToMany
    @JoinTable(
            name = "level_supply",
            joinColumns = @JoinColumn(name = "level_id"),
            inverseJoinColumns = @JoinColumn(name = "supply_id")
    )
    private List<Supply> supplies;

    // --- CONSTRUCTORS ---

    // 1. ضروري لـ JPA
    public Level() {}

    // 2. 🔥 هذا هو الكونستراكتور اللي كان خاصك باش تحيد Error من SchoolService
    // (كيقبل 5 بارامترات فقط، بلا supplies)
    public Level(Long id, String name, String slug, String cycle, School school) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.cycle = cycle;
        this.school = school;
    }

    // 3. كونستراكتور كامل (إلا احتاجيتيه فبلاصة أخرى)
    public Level(Long id, String name, String slug, String cycle, School school, List<Supply> supplies) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.cycle = cycle;
        this.school = school;
        this.supplies = supplies;
    }

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getCycle() { return cycle; }
    public void setCycle(String cycle) { this.cycle = cycle; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public List<Supply> getSupplies() { return supplies; }
    public void setSupplies(List<Supply> supplies) { this.supplies = supplies; }
}