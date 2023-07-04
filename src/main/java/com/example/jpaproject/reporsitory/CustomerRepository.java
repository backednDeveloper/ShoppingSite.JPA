package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findById(Integer id);
    CustomerEntity updateCustomerEntitiesById(Integer id);
    CustomerEntity deleteCustomerEntitiesById(Integer id);
}
