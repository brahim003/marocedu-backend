package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
