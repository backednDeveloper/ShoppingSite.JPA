package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int id;
    @Column(name = "customer_name")
    private String name;
    @Column(name = "customer_email")
    private String email;
    @Column(name = "customer_adress")
    private String adress;
    @Column(name = "customer_phone")
    private Long phone;
    @Column(name = "customer_registration_date")
    private Date registration_date;
}
