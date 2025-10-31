// Fichier: ImageRepository.java
package org.example.repository;

import org.example.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    // Le Repository est maintenant défini correctement
}