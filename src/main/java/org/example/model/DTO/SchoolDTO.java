package org.example.model.DTO;

// Had l class 3adia, machi @Entity
public class SchoolDTO {

    private Long id;
    private String name;
    private String slug;
    private String logo;
    private String city;

    // L constructor l khawi (vide) mohim bzaf 3la 9bel JSON
    public SchoolDTO() {}



    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSlug() {return slug;}
    public void setSlug(String slug) {this.slug = slug;}

    public String getLogo() {return logo;}
    public void setLogo(String logo) {this.logo = logo;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}
}