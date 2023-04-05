package com.example.usersecurity.customers;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.Upload;
import com.example.usersecurity.mapper.CustomersMapper;
import com.example.usersecurity.models.CustomersOrdersDetails;
import com.example.usersecurity.models.Orders;
import com.example.usersecurity.models.OrdersDetails;
import com.example.usersecurity.repository.OrdersDetailsCustomer;
import com.example.usersecurity.repository.OrdersDetailsRepo;
import com.example.usersecurity.repository.UploadRepo;
import com.example.usersecurity.specifications.CustomerSpecificationBuilder;
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

    private final UploadRepo uploadRepo;

    private final OrdersDetailsRepo ordersDetailsRepo;

    private final OrdersDetailsCustomer ordersDetailsCustomer;

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

    public List<Upload> showAll(){
        return uploadRepo.findAll();
    }

    public void joins(OrdersDetails orders){
        for (Customers customer : orders.getCustomersdetails()) {
            customersRepository.save(customer);
        }
        ordersDetailsRepo.save(orders);
    }

    public void joinDirect(OrdersDetails ordersDetails) {
        ordersDetailsRepo.save(ordersDetails);
        for (Customers customer : ordersDetails.getCustomersdetails()) {
            Optional<Customers> existingCustomer = customersRepository.findById(customer.getId());
            if(existingCustomer.empty().isEmpty()){
                customersRepository.save(customer);
            }
            CustomersOrdersDetails customersOrdersDetails = new CustomersOrdersDetails();
            customersOrdersDetails.setCustomer(customer);
            customersOrdersDetails.setOrderDetails(ordersDetails);
            ordersDetailsCustomer.save(customersOrdersDetails);
        }
    }

    public void join(OrdersDetails ordersDetails, Long id, Long id1){

        Optional<OrdersDetails> existingOrderDetails = ordersDetailsRepo.findByOrderLineNumber(ordersDetails.getOrderLineNumber());

        if(existingOrderDetails.isPresent()){
            ordersDetails = existingOrderDetails.get();
        } else {
            ordersDetailsRepo.save(ordersDetails);
        }

        Customers customers = customersRepository.findById(id).orElseThrow();
        Customers customers1 = customersRepository.findById(id1).orElseThrow();

        ArrayList<Customers> customersArrayList = new ArrayList<>(Arrays.asList(customers, customers1));
        for (Customers customer : customersArrayList) {
            Optional<CustomersOrdersDetails> existingJoinEntry = ordersDetailsCustomer.findByCustomerAndOrderDetails(customer,ordersDetails);

            if(existingJoinEntry.isEmpty()){
                CustomersOrdersDetails customersOrdersDetails = new CustomersOrdersDetails();
                customersOrdersDetails.setCustomer(customer);
                customersOrdersDetails.setOrderDetails(ordersDetails);
                ordersDetailsCustomer.save(customersOrdersDetails);
            }
        }
    }

}
