package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PaymentConfirmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_confirm_id")
    private int id;
    @Column(name = "confirmation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "confirmation_status")
    private int status;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;
    @OneToOne
    @JoinColumn(name = "confirm_id")
    private ConfirmEntity confirmEntity;
}
