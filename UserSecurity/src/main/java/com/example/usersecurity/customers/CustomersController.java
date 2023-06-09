package com.example.usersecurity.customers;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.Upload;
import com.example.usersecurity.models.Orders;
import com.example.usersecurity.models.OrdersDetails;
import com.example.usersecurity.repository.OrdersDetailsCustomer;
import com.example.usersecurity.repository.OrdersDetailsRepo;
import com.example.usersecurity.repository.UploadRepo;
import com.example.usersecurity.service.FileUploadSerive;
import com.example.usersecurity.specifications.CustomerSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        return customersService.showAll();
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
        customersService.joins(orders);
    }

    @PostMapping("/join/dire")
    public void joinDirect(@RequestBody OrdersDetails ordersDetails) {
        customersService.joinDirect(ordersDetails);
    }

    @PostMapping("/join/{id}/{id1}")
    public void join(@RequestBody OrdersDetails ordersDetails, @PathVariable("id") Long id, @PathVariable("id1") Long id1){
        customersService.join(ordersDetails,id,id1);
    }
}
