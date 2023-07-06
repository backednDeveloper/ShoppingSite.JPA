package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmCustomerProfilesRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findById(Integer id);
}
