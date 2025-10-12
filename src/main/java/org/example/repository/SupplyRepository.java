package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.Supply;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    Supply findByName(String name);
}

