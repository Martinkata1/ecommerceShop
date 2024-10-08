package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Customer;
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
                //new Customers(10L,"John","12341" ,"john_doe@gmail.com"),
                //new Customers(11L, "Anna", "anna_gomes@gmail.com")
        );
        model.addAttribute("customers", customers);
        return "customers";
    }


}
