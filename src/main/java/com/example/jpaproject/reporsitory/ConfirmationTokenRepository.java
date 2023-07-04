package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<TokenEntity, Integer> {
    Optional<TokenEntity> findById(Integer id);
    TokenEntity updateCustomerEntitiesById(Integer id);
    TokenEntity deleteCustomerEntitiesById(Integer id);

}
