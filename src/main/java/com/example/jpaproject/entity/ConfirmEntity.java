package com.example.jpaproject.entity;

import com.example.jpaproject.reporsitory.CustomerRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "confirm_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerEntity sellerEntity;
    @OneToOne
    @JoinColumn(name = "confirmation_token_id")
    private ConfirmationTokenEntity confirmationTokenEntity;
}
