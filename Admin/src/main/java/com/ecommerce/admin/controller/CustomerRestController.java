package com.ecommerce.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerRestController {
    @GetMapping("/users")
    public List<Customers> getAllUsers(){
        return Arrays.asList(new Customers(10L, "Ivan", "ivan_qkiq@gmail.com"),
                            new Customers(11L, "Anna", "anna_gomes@gmail.com")
        );

    }
}
