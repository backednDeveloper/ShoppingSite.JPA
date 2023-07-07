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
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int id;
    @Column(name = "token")
    private String token;
    @Column(name = "token_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date token_created_date;
    public Token( String token, Date token_created_date){}
}
