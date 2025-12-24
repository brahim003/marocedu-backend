package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.Supply;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Supply findByName(String name);
    List<Supply> findByLevelSchoolSlugAndLevelSlug(String schoolSlug, String levelSlug);

    // ✅ NOUVEAU: Method l-L-Dashboard (Low Stock Items Count)
    // Kat7sab 3adad L-Articles 7sab L-7ala dial L-InStock (Mital: false)
    Long countByInStock(Boolean inStock);
}