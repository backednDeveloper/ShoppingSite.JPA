package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PaymentConfirmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_confirm_id")
    private int id;
    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;
}
