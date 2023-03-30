package com.example.usersecurity.customers;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.mapper.CustomersMapper;
import com.example.usersecurity.specifications.CustomerSpecificationBuilder;
import com.example.usersecurity.specifications.CustomerSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CustomersService {

    private final CustomersRepository customersRepository;

    private final CustomersMapper customersMapper;
    private final CustomerSpecificationBuilder customerSpecificationBuilder;

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

}
