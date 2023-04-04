package com.example.usersecurity.customers;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.mapper.CustomersMapper;
//import com.example.usersecurity.models.Orders;
import com.example.usersecurity.models.Orders;
import com.example.usersecurity.models.OrdersDetails;
import com.example.usersecurity.repository.OrdersDetailsRepo;
import com.example.usersecurity.specifications.CustomerSpecificationBuilder;
import com.example.usersecurity.specifications.CustomerSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CustomersService {

    private final CustomersRepository customersRepository;

    private final CustomersMapper customersMapper;

    private final OrdersDetailsRepo ordersDetailsRepository;

    public void save(CustomersDto customersDto){
        customersDto.setStatus(true);
//        customersRepository.save(customersDto);
         customersRepository.save(customersMapper.dtotoModel(customersDto));
    }
    public void delete(Long customerId){
        CustomersDto customers = customersMapper.modeltoDto(customersRepository.findById(customerId).get());
        customersRepository.deleteById(customers.getId());
//        customersRepository.deleteById(customerId);
    }

    public void update(CustomersDto updatedCustomer){
//        customersRepository.save(updatedCustomer);
       customersRepository.save( customersMapper.dtotoModel(updatedCustomer));
    }
    public CustomersDto findById(Long customerId){
//       Optional<CustomersDto> customers = customersMapper.dtotoModel(customersRepository.findById(customerId));
       CustomersDto customers = customersMapper.modeltoDto(customersRepository.findById(customerId).get());
       return customers;
    }
    public List<CustomersDto> findAll(){
//        List<Customers> customers = customersRepository.findAll();
        List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersRepository.findAll());
        return customersDtos;
    }

    public Page<CustomersDto> search(Specification<Customers> specs, int offset){
        Page<Customers> customersPage =  customersRepository.findAll(specs, PageRequest.of(offset, 8));
        List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
        return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
    }

    public Page<CustomersDto> sortName(String direct, int offset) {
//        customersRepository.findAll(Sort.by("firstName","lastName"));
        if(direct.equals("asc")){
            Page<Customers> customersPage = customersRepository.findAll(PageRequest.of(offset,8,Sort.by(Sort.Direction.ASC,"firstName","lastName")));
            List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
            return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
        }
        Page<Customers> customersPage = customersRepository.findAll(PageRequest.of(offset,8,Sort.by(Sort.Direction.DESC,"firstName","lastName")));
        List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
        return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
    }

    public Page<CustomersDto> sortAddress(String direct, int offset) {
        if(direct.equals("asc")){
            Page<Customers> customersPage = customersRepository.findAll(PageRequest.of(offset,8,Sort.by(Sort.Direction.ASC,"customerAddress")));
            List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
            return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
        }
        Page<Customers> customersPage = customersRepository.findAll(PageRequest.of(offset,8,Sort.by(Sort.Direction.DESC,"customerAddress")));
        List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
        return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
    }

    public Page<CustomersDto> sortStatus(String direct, int offset) {
        if(direct.equals("asc")){
            Page<Customers> customersPage = customersRepository.findAll(PageRequest.of(offset,8,Sort.by(Sort.Direction.ASC,"status")));
            List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
            return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
        }
        Page<Customers> customersPage = customersRepository.findAll(PageRequest.of(offset,8,Sort.by(Sort.Direction.DESC,"status")));
        List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
        return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
    }

    public Page<CustomersDto> pagination(int offset) {
        Page<Customers> customersPage = customersRepository.findAll(PageRequest.of(offset, 8));
        List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
        return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
    }

    public Page<CustomersDto> filter(String query, int offset) {

        CustomerSpecificationBuilder builder = new CustomerSpecificationBuilder();
        Specification<Customers> specs = builder.build(query);
        if(specs !=null){
            Page<Customers> customersPage =  customersRepository.findAll(specs, PageRequest.of(offset, 8));
            List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
            return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
        }

        Page<Customers> customersPage = customersRepository.findAll(PageRequest.of(offset, 8));
        List<CustomersDto> customersDtos = customersMapper.modelstoDtos(customersPage.getContent());
        return new PageImpl<>(customersDtos, customersPage.getPageable(), customersPage.getTotalElements());
    }

    public void addOrderToCustomerOrder(Long customerId, Orders order) {
        Customers customer = customersRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        order.setCustomer(customer); // Set the customer object in the order object
        customer.getOrders().add(order);

        customersRepository.save(customer);
    }


//    public void addOrderToCustomer(Long customerId, Long customerid1, OrdersDetails order) {
//        Customers customer = customersRepository.findById(customerId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
//        Customers customer1 = customersRepository.findById(customerid1)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
//
//        List<Customers> customers = new ArrayList<>();
//        customers.add(customer);
//        customers.add(customer1);
//
//        order.setCustomersdetails(customers); // set customers in order
//        customer.getOrdersDetails().add(order); // add order to first customer
//        customer1.getOrdersDetails().add(order); // add order to second customer
//
//        customersRepository.save(customer); // save first customer and associated orders
//        customersRepository.save(customer1); // save second customer and associated orders
//    }

    public void addOrderToCustomer(OrdersDetails order) {
        List<Customers> existingCustomers = new ArrayList<>();

        // Load existing customers from the database
        for (Customers customer : order.getCustomersdetails()) {
            Customers existingCustomer = customersRepository.findById(customer.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            existingCustomers.add(existingCustomer);
        }

        // Set the existing customers in the order
        order.setCustomersdetails(existingCustomers);

        // Update the customers with the new order
        for (Customers customer : existingCustomers) {
            customer.getOrderDetails().add(order);
            customersRepository.save(customer);
        }

        // Save the order
        ordersDetailsRepository.save(order);
    }




//    OrdersDetails order1 = ordersDetailsRepository.findById(2L).orElse(null);
//    List<Customers> customerList = order1.getCustomersdetails();
//            for (Customers customer : customerList) {
//        System.out.println("Customer ID: " + customer.getId());
//        System.out.println("Customer Name: " + customer.getFirstName() + " " + customer.getLastName());
//        System.out.println("Customer Email: " + customer.getCustomerEmail());
//        System.out.println("Customer Phone: " + customer.getCustomerPhone());
//        System.out.println("Customer Address: " + customer.getCustomerAddress());
//        System.out.println("Customer Status: " + customer.getStatus());
//    }



}
