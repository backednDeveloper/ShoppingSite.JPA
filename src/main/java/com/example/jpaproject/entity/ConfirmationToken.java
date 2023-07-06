package com.example.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "confirmation_token_id")
    private long id;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customerEntity;
    @OneToOne(targetEntity = Token.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "confirmation_token")
    private Token token;
    @OneToOne(targetEntity = Seller.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private Seller sellerEntity;
    public ConfirmationToken(Customer customerEntity){
        this.customerEntity=customerEntity;
        this.token= new Token(String.valueOf(UUID.randomUUID()), date);
        this.date = new Date();
    }
    public ConfirmationToken(Seller sellerEntity){
        this.sellerEntity=sellerEntity;
        this.token= new Token(String.valueOf(UUID.randomUUID()) , date);
        this.date= new Date();
    }
}
