package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class CustomerRestController {
    private final CustomerRepository customerRepository;

    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/users")
    public List<Customer> fetchAllUsers() {
        return customerRepository.findAll();
    }

    // New Endpoint to Fetch and Send Data to Admin
    @GetMapping("/users")
    public List<Customer> fetchAllUsersForAdmin() {
        return customerRepository.findAll();
    }

}
