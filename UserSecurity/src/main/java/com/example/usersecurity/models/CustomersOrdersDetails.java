package com.example.usersecurity.models;

import com.example.usersecurity.customers.Customers;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customers_orders_details")
@Data
public class CustomersOrdersDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_details_id")
    private OrdersDetails orderDetails;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customer;

}

