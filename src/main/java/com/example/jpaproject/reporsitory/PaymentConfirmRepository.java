package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.PaymentConfirm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentConfirmRepository extends JpaRepository<PaymentConfirm, Integer> {
    Optional<PaymentConfirm> findById(Integer id);
}
