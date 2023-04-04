package com.example.usersecurity.customers;

import com.example.usersecurity.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long>, JpaSpecificationExecutor<Customers> {

    List<Customers> findAll();

    @Query("SELECT o FROM Orders o WHERE o.customer.id = :customerId")
    List<Orders> findOrdersByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT AVG(o.price) FROM Orders o")
    Long findAverage();

    @Query("SELECT o FROM Orders o WHERE o.price > (SELECT AVG(o2.price) FROM Orders o2) AND o.customer.id = 2")
    List<Orders> findMoreThanAvg();

    @Query("SELECT c.customerEmail, o.status, o.comments FROM Orders o JOIN o.customer c WHERE o.price > (SELECT AVG(o2.price) FROM Orders o2) AND c.id = 2")
    List<Object[]> findCustomerEmailAndCommentsById(@Param("customerId") Long customerId);

    @Query("SELECT c.customerEmail, COUNT(o.id) FROM Customers c JOIN c.orders o GROUP BY c.customerEmail")
    List<Object[]> joinTables(Long id);
}

