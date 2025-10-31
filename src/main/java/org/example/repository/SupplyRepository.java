package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.Supply;

import java.util.List; // Import nécessaire pour retourner une liste

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Supply findByName(String name);
    List<Supply> findByLevelSchoolSlugAndLevelSlug(String schoolSlug, String levelSlug);
}