package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "shipping_adress")
    private String adress;
    @Column(name = "order_weight")
    private int weight;
    @Column(name = "total_price")
    private int price;
    @Column(name = "order_status")
    private int status;
    @Column(name = "order_date")
    private Date date;
    @ManyToMany
    @JoinTable(name = "Products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> ProductentityList;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;
}
