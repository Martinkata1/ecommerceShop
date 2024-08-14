package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Locale;

/**
 * Controller for accessing the home page
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
    /**
     * Service used to manage customer-related operations, such as retrieving customer information
     */
    @Autowired
    private  CustomerService customerService;

    /**
     * Maps the root URL / and /index to the home method. This handles the default
     * landing page of the website.
     * Model Attributes: The model is populated with the title and page attributes, which
     * can be used in the view to display the page title and other relevant information.
     * User Authentication Check: If the user is logged in (checked via the Principal object),
     * the controller retrieves the customer's full name and stores it in the session.
     * Shopping Cart Management: If the customer has a shopping cart, the total number of items
     * in the cart is stored in the session. This data can be displayed in the header or elsewhere
     * on the site to indicate the number of items in the cart.
     * @param model
     * @param principal
     * @param session
     * @return "home"
     */
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session, HttpServletRequest request) {
        model.addAttribute("title", "Home");
        model.addAttribute("page", "Home");

        /**
         * Understand Locale in Spring Boot Internationalization
         * After reseting the site, it will show information about languages
         */
        Locale currentLocale = request.getLocale();
        String countryCode = currentLocale.getCountry();
        String countryName = currentLocale.getDisplayCountry();

        String LangCode= currentLocale.getLanguage();
        String LangName= currentLocale.getDisplayLanguage();

        System.out.println(countryCode + ": " + countryName);
        System.out.println(LangCode + ": " + LangName);
        System.out.println("=================================");
        String[] languages = Locale.getISOCountries();

        for(String language : languages){
            Locale locale = new Locale(language);
            System.out.println(language + ": " + locale.getDisplayLanguage());
        }

        if (principal != null) {
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
            ShoppingCart shoppingCart = customer.getCart();
            if (shoppingCart != null) {
                session.setAttribute("totalItems", shoppingCart.getTotalItems());
            }
        }
        return "home";
    }

    /**
     * Maps the /contact URL to the contact method, handling requests for the contact page.
     * Model Attributes: Adds the title and page attributes for the contact page to the model. These attributes can be used to dynamically set the page title and other related information in the view.
     * @param model
     * @return the contact method
     */
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contact");
        model.addAttribute("page", "Contact");
        return "contact-us";
    }

    /**
     * URL to the getInfo method, handling requests for the information page.
     * Model Attributes: Adds the title and page attributes for the information page to the model.
     * @param model
     * @return the getInfo method
     */
    @GetMapping("/info")
    public String getInfo(Model model) {
        model.addAttribute("title", "Information");
        model.addAttribute("page", "Information");
        return "info";
    }

}
