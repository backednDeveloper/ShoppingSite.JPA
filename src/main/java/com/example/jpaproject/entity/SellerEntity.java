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
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seller_id")
    private int id;
    @Column(name = "seller_status")
    private int status;
    @Column(name = "seller_name")
    private String name;
    @Column(name = "seller_email")
    private String email;
    @Column(name = "seller_adress")
    private String adress;
    @Column(name = "seller_phone")
    private Long phone;
    @Column(name = "seller_registration_date_")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registration_date;
}
