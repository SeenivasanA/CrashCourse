package com.example.usersecurity.customers;

//import com.example.usersecurity.models.Orders;
import com.example.usersecurity.models.Orders;
import com.example.usersecurity.models.OrdersDetails;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Orders> orders;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "customers_orders_details",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_details_id", referencedColumnName = "id")
    )    private List<OrdersDetails> orderDetails;
}