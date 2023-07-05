package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findById(Integer id);
}
