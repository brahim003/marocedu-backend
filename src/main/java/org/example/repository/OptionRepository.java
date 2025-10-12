package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    Option findByName(String name);
}
