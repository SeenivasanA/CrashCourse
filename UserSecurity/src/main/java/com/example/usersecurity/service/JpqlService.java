package com.example.usersecurity.service;

import com.example.usersecurity.DTO.OrdersDto;
import com.example.usersecurity.customers.Customers;
import com.example.usersecurity.customers.CustomersRepository;
import com.example.usersecurity.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpqlService {

    @Autowired
    public CustomersRepository customersRepository;

    @Autowired
    public OrdersMapper ordersMapper;

    public List<OrdersDto> gettingOrders(Long id){
        List<OrdersDto> orders = ordersMapper.modelstoDtos(customersRepository.findOrdersByCustomerId(id));
        return orders;
    }

    public Long findAvg(){
        return customersRepository.findAverage();
    }

    public List<Customers> gettingOrders(){
        List<Customers> customers = customersRepository.findAll();
        return customers;
    }

    public List<OrdersDto> moreAvg(){
        List<OrdersDto> orders = ordersMapper.modelstoDtos(customersRepository.findMoreThanAvg());
        return orders;
    }

    public Object getTwoRow(Long id){
        return customersRepository.findCustomerEmailAndCommentsById(id);
    }

    public  List<Object[]> getJoins(Long id){
        return customersRepository.joinTables(id);
    }
}
