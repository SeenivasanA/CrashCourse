package com.example.usersecurity.models;

import com.example.usersecurity.customers.Customers;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name="orders_details")
@Data
    public class OrdersDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long id;

        public Integer productCode;
        public Integer quantityOrdered;
        public BigInteger price;
        public Integer orderLineNumber;

        @ManyToMany(mappedBy = "orderDetails", fetch = FetchType.LAZY)
        private List<Customers> customersdetails;
    }
