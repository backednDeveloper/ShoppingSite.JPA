package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_entitys")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_price")
    private int price;
    @Column(name = "product_weight")
    private int weight;
    @Column(name = "product_category")
    private String category;
    @OneToOne
    @JoinColumn(name = "confirm_id")
    private Confirm confirmEntity;
}
