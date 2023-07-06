package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmSellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findById(Integer id);
}
