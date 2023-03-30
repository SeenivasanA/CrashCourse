package com.example.usersecurity.mapper;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.customers.Customers;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomersMapper {

    CustomersDto modeltoDto(Customers customers);

    @InheritInverseConfiguration
    Customers dtotoModel(CustomersDto customersDto);

    List<CustomersDto> modelstoDtos(List<Customers> customers);

}

