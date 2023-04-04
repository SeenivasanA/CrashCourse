package com.example.usersecurity.customers;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.Upload;
import com.example.usersecurity.models.CustomersOrdersDetails;
import com.example.usersecurity.models.Orders;
import com.example.usersecurity.models.OrdersDetails;
import com.example.usersecurity.repository.OrdersDetailsCustomer;
import com.example.usersecurity.repository.OrdersDetailsRepo;
import com.example.usersecurity.repository.UploadRepo;
import com.example.usersecurity.service.FileUploadSerive;
import com.example.usersecurity.specifications.CustomerSpecifications;
import javassist.bytecode.ByteArray;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CustomersController {

    public final CustomersService customersService;

    @Autowired
    public FileUploadSerive fileUploadSerive;
    @Autowired
    public OrdersDetailsRepo ordersDetailsRepo;
    private final CustomersRepository customersRepository;


    private final OrdersDetailsCustomer ordersDetailsCustomer;

    private final UploadRepo uploadRepo;

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

    @PostMapping("/upload")
    public void uploadLocal(@RequestParam("file")MultipartFile multipartFile){
        fileUploadSerive.uploadToLocal(multipartFile);
    }

    @PostMapping("/upload/db")
    public void uploadToDb(@RequestParam("file")MultipartFile multipartFile){
        fileUploadSerive.uploadToDb(multipartFile);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadById(@PathVariable("id") Long id){
        Upload file =  fileUploadSerive.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename= "+file.getFileName())
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/uploads")
    public List<Upload> showAll(){
        return uploadRepo.findAll();
    }

    @DeleteMapping ("/upload/delete/{id}")
    public void deleteUpload(@PathVariable("id") Long id){
        fileUploadSerive.deleteUpload(id);
    }

    @PostMapping("/join/{id}")
    public void join(@RequestBody Orders orders, @PathVariable("id") Long id){
        customersService.addOrderToCustomerOrder(id,orders);
    }

    @PostMapping("/join")
    public void join(@RequestBody OrdersDetails orders){
        customersService.addOrderToCustomer(orders);
    }

    @PostMapping("/join/direct")
    public void joins(@RequestBody OrdersDetails orders){
        for (Customers customer : orders.getCustomersdetails()) {
            customersRepository.save(customer);
        }
        ordersDetailsRepo.save(orders);
    }

    @PostMapping("/join/dire")
    public void joinDirect(@RequestBody OrdersDetails ordersDetails) {
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

    @PostMapping("/join/{id}/{id1}")
    public void join(@RequestBody OrdersDetails ordersDetails, @PathVariable("id") Long id, @PathVariable("id1") Long id1){

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
