package com.example.usersecurity.specifications;

import com.example.usersecurity.DTO.CustomersDto;
import com.example.usersecurity.customers.Customers;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class CustomerSpecifications implements Specification<CustomersDto> {

    public static Specification<Customers> likeFirstName(String firstname){
        if(firstname ==null){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get("firstName"), "%"+ firstname+ "%" );
        };
    }

    public static Specification<Customers> likeLastName(String lastname){
        if(lastname ==null){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get("lastName"), "%"+ lastname+ "%" );
        };
    }

    public static Specification<Customers> likeEmail(String email){
        if(email == null){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get("customerEmail"), "%"+email+"%" );
        };
    }

    public static Specification<Customers> hasAddress(Object addresses){
        if(addresses == null){
            return null;
        }
        return (root, query, cb) -> {
            if (addresses instanceof String) {
                // single address
                String address = (String) addresses;
                return cb.like(root.get("customerAddress"), "%" + address + "%");
            } else if (addresses instanceof String[]) {
                // multiple addresses
                String[] addressArray = (String[]) addresses;
                List<Predicate> predicates = new ArrayList<>();
                for (String address : addressArray) {
                    Predicate predicate = cb.like(root.get("customerAddress"), "%" + address + "%");
                    predicates.add(predicate);
                }
                return cb.or(predicates.toArray(new Predicate[predicates.size()]));
            }
            return null;
        };
    }


    public static Specification<Customers> status(String status){
        if(status ==null){
            return null;
        }
        return (root, query, cb) -> {
            if (status.equalsIgnoreCase("true")) {
                return cb.isTrue(root.get("status"));
            } else if (status.equalsIgnoreCase("false")) {
                return cb.isFalse(root.get("status"));
            } else {
                throw new IllegalArgumentException("Invalid status value: " + status);
            }
        };
    };
}
