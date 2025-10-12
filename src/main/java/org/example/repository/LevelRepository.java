package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    Level findByName(String name);
}
