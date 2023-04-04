package com.example.usersecurity.models;

import com.example.usersecurity.customers.Customers;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="orders")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String comments;
    @Column(name="ordered_date")
    public Date orderedDate;
    @Column(name="shipped_date")
    public Date shippedDate;
    public BigInteger price;
    public String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customers customer;
}
