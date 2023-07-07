package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_confirms")
public class PaymentConfirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_confirm_id")
    private int id;
    @Column(name = "confirmation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "confirmation_status")
    private int status;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order orderEntity;
    @OneToOne
    @JoinColumn(name = "confirm_id")
    private Confirm confirmEntity;
}
