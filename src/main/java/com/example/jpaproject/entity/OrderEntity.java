package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

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
    @ManyToMany
    @JoinTable(name = "Customers",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<CustomerEntity> CustomerEntityList;
    @ManyToMany
    @JoinTable(name = "Seller",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "seller_id")
    )
    private List<SellerEntity> SellerEntityList;
    @ManyToMany
    @JoinTable(name = "Products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> ProductentityList;
}
