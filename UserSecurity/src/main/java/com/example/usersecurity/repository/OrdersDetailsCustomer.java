package com.example.usersecurity.repository;

import com.example.usersecurity.customers.Customers;
import com.example.usersecurity.models.CustomersOrdersDetails;
import com.example.usersecurity.models.OrdersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface OrdersDetailsCustomer extends JpaRepository<CustomersOrdersDetails, Long> {

    Optional<CustomersOrdersDetails> findByCustomerAndOrderDetails(Customers customer, OrdersDetails ordersDetails);
}
