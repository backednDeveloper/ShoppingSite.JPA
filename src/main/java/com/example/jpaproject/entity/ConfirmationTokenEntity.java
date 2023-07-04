package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Confirmation_Token_id")
    private long id;
    @Column(name = "confirmation_token")
    private String token;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToOne(targetEntity = CustomerEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", nullable = false)
    private CustomerEntity customerEntity;
    @OneToOne(targetEntity = SellerEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", nullable = false)
    private SellerEntity sellerEntity;
    public ConfirmationTokenEntity(CustomerEntity customerEntity){
        this.customerEntity=customerEntity;
        this.token= String.valueOf(UUID.randomUUID());
        this.date = new Date();
    }
    public ConfirmationTokenEntity(SellerEntity sellerEntity){
        this.sellerEntity=sellerEntity;
        this.date=new Date();
        this.token= String.valueOf(UUID.randomUUID());
    }
}
