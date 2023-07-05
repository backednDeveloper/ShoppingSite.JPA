package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "confirms")
public class Confirm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "confirm_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerEntity;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller sellerEntity;
    @OneToOne
    @JoinColumn(name = "confirmation_token_id", nullable = false)
    private ConfirmationToken confirmationTokenEntity;
    @Column(name = "confirm_status")
    private int status;
}
