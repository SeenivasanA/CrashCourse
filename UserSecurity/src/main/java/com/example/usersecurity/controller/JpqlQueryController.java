package com.example.usersecurity.controller;

import com.example.usersecurity.DTO.OrdersDto;
import com.example.usersecurity.customers.Customers;
import com.example.usersecurity.customers.CustomersRepository;
import com.example.usersecurity.mapper.OrdersMapper;
import com.example.usersecurity.models.Orders;
import com.example.usersecurity.service.JpqlService;
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

    public JpqlService jpqlService;

    @GetMapping()
    public List<Customers> gettingOrders(){
        return jpqlService.gettingOrders();
    }
    @GetMapping("/{id}/orders")
    public List<OrdersDto> gettingOrders(@PathVariable("id") Long id){
       return jpqlService.gettingOrders(id);
    }

    @GetMapping("/avg")
    public Long avg(){
        return jpqlService.findAvg();
    }

    @GetMapping("/Avg")
    public List<OrdersDto> moreAvg(){
       return jpqlService.moreAvg();
    }

    @GetMapping("/object/{id}")
    public Object getTwoRow(@PathVariable("id") Long id){
        return jpqlService.getTwoRow(id);
    }

    @GetMapping("/join/{id}")
    public  List<Object[]> getJoins(@PathVariable("id") Long id){
        return jpqlService.getJoins(id);
    }

}
