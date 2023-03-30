package com.example.usersecurity.DTO;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Data
@Setter
@Getter
public class CustomersDto {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String customerEmail;
    private String customerPassword;
    private Long customerPhone;
    private String customerAddress;
    private Boolean status;
}

