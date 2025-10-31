package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.Level;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

    // Trouver un niveau par son nom
    Level findByName(String name);

    // Trouver tous les niveaux d'une école via le slug de l'école
    // Option 1 : avec JPQL explicite
    @Query("SELECT l FROM Level l WHERE l.school.slug = :slug")
    List<Level> findBySchoolSlug(@Param("slug") String slug);

    // Option 2 : méthode dérivée Spring Data JPA
    // List<Level> findBySchool_Slug(String slug);
    // (Vous pouvez utiliser cette ligne à la place de l'annotation @Query)
}
