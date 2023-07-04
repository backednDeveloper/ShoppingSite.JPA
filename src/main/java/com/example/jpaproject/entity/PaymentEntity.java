package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "Payment_id")
    private int id;
    @Column(name = "cart_number")
    private Long number;
    @Column(name = "cart_password")
    private Long password;
    @Column(name = "cart_cvv")
    private int cvv;

}
