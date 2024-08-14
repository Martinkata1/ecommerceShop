package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for customer login
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
    /**
     * CustomerService: Service that handles customer-related operations such as retrieving and saving customer data.
     * BCryptPasswordEncoder: Encoder used to hash and verify passwords securely.
     */
    @Autowired
    private  CustomerService customerService;
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

    /**
     *  Maps the /login URL to the login method, handling GET requests for the login page.
     * Model Attributes: The model is populated with the title and page attributes, which are used in the view to set the page title and other related data.
     * @param model
     * @return "login"
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        model.addAttribute("page", "Home");
        return "login";
    }


    /**
     * URL to the register method, handling GET requests for the registration page.
     * Model Attributes: The model is populated with title, page, and an empty CustomerDto object to be filled by the user during registration.
     * @param model
     * @return "register"
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("page", "Register");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }


    /**
     * Maps the /do-register URL to the registerCustomer method, handling POST requests for user registration.
     * Input Validation: The @Valid annotation ensures that the customerDto object is validated according to the constraints defined in the DTO. The BindingResult object captures validation errors.
     * Error Handling:
     * If there are validation errors, the form is re-displayed with the errors highlighted.
     * If the username (email) is already registered, an error message is shown.
     * If the password and confirm password do not match, an error message is shown.
     * If an exception occurs during registration, a generic error message is shown.
     * Password Encryption: If the form is valid and the passwords match, the password is encrypted using BCryptPasswordEncoder before saving the customer data.
     * Success Handling: If registration is successful, a success message is displayed on the registration page.
     * @param customerDto
     * @param result
     * @param model
     * @return true if registration is successful, false otherwise
     */
    @PostMapping("/do-register")
    public String registerCustomer(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                   BindingResult result,
                                   Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            String username = customerDto.getUsername();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Email has been register!");
                return "register";
            }
            if (customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("success", "Register successfully!");
            } else {
                model.addAttribute("error", "Password is incorrect");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server is error, try again later!");
        }
        return "register";
    }


}
