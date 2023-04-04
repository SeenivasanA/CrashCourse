package com.example.usersecurity.mapper;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.DTO.OrdersDto;
import com.example.usersecurity.customers.Customers;
import com.example.usersecurity.models.Orders;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    OrdersDto modelToDto(Orders orders);

    @InheritInverseConfiguration
    Orders dtoToModel(OrdersDto ordersDto);

    List<OrdersDto> modelstoDtos(List<Orders> orders);
}
