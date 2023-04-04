package com.example.usersecurity.repository;

import com.example.usersecurity.models.OrdersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface OrdersDetailsRepo extends JpaRepository<OrdersDetails,Long> {

    Optional<OrdersDetails> findByOrderLineNumber(Integer Id);
}
