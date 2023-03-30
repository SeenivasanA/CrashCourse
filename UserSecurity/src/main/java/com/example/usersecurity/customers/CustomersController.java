package com.example.usersecurity.customers;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.specifications.CustomerSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CustomersController {

    public final CustomersService customersService;

    @GetMapping
    public List<CustomersDto> getAllCustomer(){
//        List<Customers> customers = customersService.findAll();
        List<CustomersDto> customersDtos = customersService.findAll();
        return customersDtos;
    }

    @GetMapping("/edit/{customerId}")
    public CustomersDto createCustomer(@PathVariable("customerId") Long customerId){
//        Optional<Customers> customers = customersService.findById(customerId);
        CustomersDto customers = customersService.findById(customerId);
        return customers;

    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Long customerId){
        customersService.delete(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public void updateCustomer(@RequestBody CustomersDto customers){
        customersService.update(customers);
    }

    @PostMapping
    public ResponseEntity<String> savingCustomers(@RequestBody CustomersDto customersDto){
        customersService.save(customersDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/{offset}/{data}")
    public Page<CustomersDto> search(@PathVariable("data") String data,@PathVariable("offset") int offset){
        Specification<Customers> specs = Specification.where(CustomerSpecifications.likeFirstName(data).or(CustomerSpecifications.likeLastName(data)).or(CustomerSpecifications.likeEmail(data)));
        return customersService.search(specs, offset);
    }

    @GetMapping("/sort/name/{offset}/{direction}")
    public Page<CustomersDto> sortName(@PathVariable("direction") String direct,@PathVariable("offset") int offset){
        return customersService.sortName(direct,offset);
    }

    @GetMapping("/sort/Address/{offset}/{direction}")
    public Page<CustomersDto> sortAddress(@PathVariable("direction") String direct,@PathVariable("offset") int offset){
        return customersService.sortAddress(direct, offset);
    }

    @GetMapping("/sort/status/{offset}/{direction}")
    public Page<CustomersDto> sortStatus(@PathVariable("direction") String direct,@PathVariable("offset") int offset){
        return customersService.sortStatus(direct, offset);
    }

    @GetMapping("/{offset}")
    public Page<CustomersDto> pagination(@PathVariable("offset") int offset){
        return customersService.pagination(offset);
    }

    @GetMapping("/filter/{offset}/{query}")
    public Page<CustomersDto> filter(@PathVariable("query") String query, @PathVariable("offset") int offset){
        return customersService.filter(query,offset);
    }
}
