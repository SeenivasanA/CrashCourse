package com.example.usersecurity.specifications;

import com.example.usersecurity.customers.Customers;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerSpecificationBuilder {

    public Specification<Customers> build(String query) {
        Map<String, Object> queryParams = new HashMap<>();
        String cleanedQueryString = query.substring(query.indexOf("=") + 1); // Remove "query" word
        String[] keyValuePairs = cleanedQueryString.split(",");
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split("@");
            String key = keyValue[0];
            String value = keyValue[1];
            if (value.contains("#")) {
                String[] options = value.split("#");
                queryParams.put(key, options);
            } else {
                queryParams.put(key, value);
            }
        }

        Specification<Customers> specs = Specification.where(null); // Default specification
        if (queryParams.get("isActive") != null && !queryParams.get("isActive").equals("null")) {
            String active =(String) queryParams.get("isActive");
            specs = Specification.where(CustomerSpecifications.status(active));
        }
        if (queryParams.containsKey("isAddress")) {
            Object value = queryParams.get("isAddress");
            if (value instanceof String) {
                // single value
                String address = (String) value;
                specs = Specification.where(specs).and(CustomerSpecifications.hasAddress(address));
            } else if (value instanceof String[]) {
                // array of values
                String[] address = (String[]) value;
                if (address.length > 0) {
                    specs = Specification.where(specs).and(CustomerSpecifications.hasAddress(address));
                }
            }
        }
        if (queryParams.get("customer") != null && !queryParams.get("customer").equals("null")) {
            var data =(String) queryParams.get("customer");
            specs = Specification.where(CustomerSpecifications.likeFirstName(data).or(CustomerSpecifications.likeLastName(data)).or(CustomerSpecifications.likeEmail(data)));
        }

     return specs;
    }
}
