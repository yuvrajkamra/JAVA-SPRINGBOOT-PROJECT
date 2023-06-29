package com.example.ewallet.demo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Long userId;


    @Column(unique = true)
    private String phoneNumber;

    private Double balance;

    private String identifierValue;

    @Enumerated(value=EnumType.STRING) // IT STORES ENUM AS STRING IN DB   //USED FOR ITR PURPOSES
    private UserIdentifier userIdentifier;




}
