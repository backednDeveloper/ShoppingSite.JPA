package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Integer id);

    Customer deleteById(Integer id);
}
