package com.example.usersecurity.customers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="customers")
@Data
@Setter
@Getter
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "customer_password")
    private String customerPassword;
    @Column(name = "customer_phone")
    private Long customerPhone;
    @Column(name = "customer_address")
    private String customerAddress;
    @Column(name = "status")
    private Boolean status;
}
