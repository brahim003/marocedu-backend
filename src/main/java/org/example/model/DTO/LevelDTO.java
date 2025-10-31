package org.example.model.DTO;

public class LevelDTO {

    private String name;
    private String slug;
    private String cycle; // <-- add this

    public LevelDTO() {}

    public LevelDTO(String name, String slug, String cycle) {
        this.name = name;
        this.slug = slug;
        this.cycle = cycle;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getCycle() { return cycle; } // getter
    public void setCycle(String cycle) { this.cycle = cycle; } // setter
}
