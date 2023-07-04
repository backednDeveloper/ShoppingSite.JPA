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
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private int id;
    @Column(name = "token")
    private String token;
    @Column(name = "token_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date token_created_date;
    @Column(name = "token_last_used_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date token_last_used_date;

}
