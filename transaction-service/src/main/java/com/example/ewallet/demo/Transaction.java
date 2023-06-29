package com.example.ewallet.demo;


import jakarta.persistence.*;
import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String transactionId;
    private String sender;

    private String receiver;
    private String purpose;

    @Enumerated(value=EnumType.STRING)
    private TransactionStatus transactionStatus;

    private Double amount;

    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date upadtedOn;
}
