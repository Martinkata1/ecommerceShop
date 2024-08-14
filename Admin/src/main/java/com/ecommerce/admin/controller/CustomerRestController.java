package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class CustomerRestController {
    private List<Customer> users = new ArrayList<>();
    @GetMapping
    public List<Customer> getAllUsers() {
        return users;
    }
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        //customer.setId((Long)(users.size() + 1));
        customer.setId(Long.valueOf(users.size() + 1));
        users.add(customer);
        return customer;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        Optional<Customer> userToDelete = users.stream()
                .filter(user -> user.getId().equals(id)).findFirst();
        if (userToDelete.isPresent()) {
            users.remove(userToDelete.get());
            return "User with ID " + id + " deleted successfully.";
        } else {
            return "User not found.";
        }
    }


}
