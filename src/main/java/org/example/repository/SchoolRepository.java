package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.School;
import java.util.Optional;


@Repository

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByName(String name);
    Optional<School> findBySlug(String slug); // ضروري باش findBySlug تعرفها
}
