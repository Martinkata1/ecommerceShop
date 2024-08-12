package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.City;
import com.ecommerce.library.model.Country;
import com.ecommerce.library.service.CityService;
import com.ecommerce.library.service.CountryService;
import com.ecommerce.library.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

/**
 * Controller for customer authentication
 */
@Controller
@RequiredArgsConstructor
public class CustomerController {
    /**
     * CustomerService: Service for handling customer-related operations.
     * CountryService: Service for managing country data.
     * PasswordEncoder: Utility for encoding and matching passwords.
     * CityService: Service for managing city data.
     */
    private final CustomerService customerService;
    private final CountryService countryService;
    private final PasswordEncoder passwordEncoder;
    private final CityService cityService;

    /**
     * @GetMapping("/profile"): Handles GET requests for viewing the customer's profile.
     * Principal Authentication: Checks if the user is logged in by inspecting the Principal. If not logged in, redirects to the login page.
     * Fetching Data: Retrieves the customer’s information, list of countries, and cities to populate the profile page.
     * Model Population: Adds the customer data, countries, and cities to the model for rendering in the view.
     * @param model
     * @param principal
     * @return "customer-information"
     */
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        CustomerDto customer = customerService.getCustomer(username);
        List<Country> countryList = countryService.findAll();
        List<City> cities = cityService.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countryList);
        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");
        return "customer-information";

    }

    /**
     * @PostMapping("/update-profile"): Handles POST requests for updating the profile.
     * Form Validation: Uses @Valid and BindingResult to validate the form input. If validation fails, the form is re-rendered.
     * Customer Update: Calls customerService.update(customerDto) to update the customer's profile.
     * RedirectAttributes: Flash attributes are used to pass success messages after a redirect.
     * @param customerDto
     * @param result
     * @param attributes
     * @param model
     * @param principal
     * @return "redirect:/profile"
     */
    @PostMapping("/update-profile")
    public String updateProfile(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                BindingResult result,
                                RedirectAttributes attributes,
                                Model model,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        CustomerDto customer = customerService.getCustomer(username);
        List<Country> countryList = countryService.findAll();
        List<City> cities = cityService.findAll();
        model.addAttribute("countries", countryList);
        model.addAttribute("cities", cities);
        if (result.hasErrors()) {
            return "customer-information";
        }
        customerService.update(customerDto);
        CustomerDto customerUpdate = customerService.getCustomer(principal.getName());
        attributes.addFlashAttribute("success", "Update successfully!");
        model.addAttribute("customer", customerUpdate);
        return "redirect:/profile";

    }

    /**
     * @GetMapping("/change-password"): Handles GET requests for rendering the password change form.
     * Model Attributes: Adds the title and page information to the model for rendering.
     * @param model
     * @param principal
     * @return "change-password"
     */
    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        return "change-password";
    }

    /**
     * Password Validation: Ensures that the old password matches, the new password is different, the new password and repeated password match, and the new password meets the length requirement.
     * Updating the Password: If all conditions are met, the new password is encoded and saved using customerService.changePass(customer).
     * Feedback to User: Provides feedback messages (success or error) to the user.
     * @param oldPassword
     * @param newPassword
     * @param repeatPassword
     * @param attributes
     * @param model
     * @param principal
     * @return true if the password is changed successfully, false otherwise
     */
    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("repeatNewPassword") String repeatPassword,
                             RedirectAttributes attributes,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.getCustomer(principal.getName());
            if (passwordEncoder.matches(oldPassword, customer.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, customer.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 5) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                customerService.changePass(customer);
                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "Your password is wrong");
                return "change-password";
            }
        }
    }
}
