package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.Order;
import com.example.jpaproject.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

}
