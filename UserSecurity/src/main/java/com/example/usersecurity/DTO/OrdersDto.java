package com.example.usersecurity.DTO;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="orders")
@Data
public class OrdersDto {

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

}

