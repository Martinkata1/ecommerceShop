package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * The code you provided represents a controller in Spring MVC
 * called AuthController. This controller is responsible for managing the
 * authentication and registration of administrators in the application.
 * The controller is annotated with @Controller, which makes a Spring component
 * responsible for handling HTTP requests and returning reduced views.
 */
@Controller
@RequiredArgsConstructor //it is from Lombok which create automated constructor for all final fields( as arguments)
public class AuthController {
    /**
     *A service layer that takes care of the business logic.
     *is introduced to the administrators.
     */
    private final AdminService adminService;
    /**
     * Encrypt admin passwords before storage.
     */
    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * This method handles requests to the URL /login.
     * Adds a "Login Page" title to the model and returns the name of the login view.
     * @param model
     * @return the login page (login page).
     */
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    /**
     * Handles requests to the URL /index.
     * Checks if the user is authentic. If not, redirect it to the login page (/login).
     * If authentic, returns the index view.
     * @param model
     * @return the index view (home page).
     */
    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "index";
    }

    /**
     * Handles requests to URL /register.
     * Adds a new object of type AdminDto to the model to be used for registration format.
     * @param model
     * @return the registration page
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    /**Handles requests to URL /forgot-password.
     * @param model
     * @return the forgot password page
     */
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    /**
     * Handles a POST request to the URL /register-new.
     * Checks if a user with the given username exists.
     * If the passwords match, the password is encrypted with
     * BCryptPasswordEncoder and the new admin is saved to the
     * database via adminService.
     * In case of error (for example, password mismatch or user
     * already registered), error messages are returned.
     * @param adminDto
     * @param result
     * @param model
     * @return the registration page
     */
    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model) {

        try {

            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("admin not null");
                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("adminDto", adminDto);
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";

    }
}
