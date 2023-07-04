package com.example.jpaproject.reporsitory;

import com.example.jpaproject.entity.ConfirmationTokenEntity;
import com.example.jpaproject.entity.CustomerEntity;
import com.example.jpaproject.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Integer> {
    Optional<ConfirmationTokenEntity> findById(Integer id);

}
