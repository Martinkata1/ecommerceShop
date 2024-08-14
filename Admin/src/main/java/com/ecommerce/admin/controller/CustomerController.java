package com.ecommerce.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class CustomerController {
    @GetMapping("/customers")
    public String getCustomerPage(Model model){
        List<Customers> customers = Arrays.asList(
                new Customers(10L, "Ivan", "ivan_qkiq@gmail.com"),
                new Customers(11L, "Anna", "anna_gomes@gmail.com")
        );
        model.addAttribute("customers", customers);
        return "customers";
    }
}
