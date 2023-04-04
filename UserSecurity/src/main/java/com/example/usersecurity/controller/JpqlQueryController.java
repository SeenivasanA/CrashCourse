package com.example.usersecurity.controller;

import com.example.usersecurity.DTO.OrdersDto;
import com.example.usersecurity.customers.Customers;
import com.example.usersecurity.customers.CustomersRepository;
import com.example.usersecurity.mapper.OrdersMapper;
import com.example.usersecurity.models.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/check")
public class JpqlQueryController {

    @Autowired
    public CustomersRepository customersRepository;

    @Autowired
    public OrdersMapper ordersMapper;

    @GetMapping()
    public List<Customers> gettingOrders(){
        List<Customers> customers = customersRepository.findAll();
        return customers;
    }
    @GetMapping("/{id}/orders")
    public List<OrdersDto> gettingOrders(@PathVariable("id") Long id){
        List<OrdersDto> orders = ordersMapper.modelstoDtos(customersRepository.findOrdersByCustomerId(id));
        return orders;
    }

    @GetMapping("/avg")
    public Long avg(){
        return customersRepository.findAverage();
    }

    @GetMapping("/Avg")
    public List<OrdersDto> moreAvg(){
        List<OrdersDto> orders = ordersMapper.modelstoDtos(customersRepository.findMoreThanAvg());
        return orders;
    }

    @GetMapping("/object")
    public Object getTwoRow(){
        return customersRepository.findCustomerEmailAndCommentsById(2L);
    }

    @GetMapping("/join")
    public  List<Object[]> getJoins(){
        return customersRepository.joinTables(2L);
    }

}
