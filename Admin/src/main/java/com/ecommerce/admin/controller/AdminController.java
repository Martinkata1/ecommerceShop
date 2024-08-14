package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final RestTemplate restTemplate;

    @Autowired
    public AdminController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/users")
    public List<Customer> getUsersFromCustomerService() {
        String customerServiceUrl = "http://localhost:8020/admin/users";
        return restTemplate.getForObject(customerServiceUrl, List.class);
    }
}
