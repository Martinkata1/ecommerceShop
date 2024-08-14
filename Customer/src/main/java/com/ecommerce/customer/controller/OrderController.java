package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.*;
import com.ecommerce.library.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

/**
 * Controller for accessing the order page
 */
@Controller
@RequiredArgsConstructor
public class OrderController {
    /**
     * CustomerService: Handles customer-related operations.
     * OrderService: Manages orders and order-related operations.
     * ShoppingCartService: Handles shopping cart operations.
     * CountryService and CityService: Provide geographic data,
     * used primarily for customer profile information.
     */
    @Autowired
    private  CustomerService customerService;
    @Autowired
    private  OrderService orderService;
    @Autowired
    private  ShoppingCartService cartService;
    @Autowired
    private  CountryService countryService;
    @Autowired
    private  CityService cityService;

    /**
     * This method handles the checkout process.
     * Authentication Check: If the user is not authenticated
     * (principal == null), they are redirected to the login page.
     * Customer Information Validation: If the customer's profile
     * lacks necessary information (like address, city, or phone number),
     * they are prompted to update it before proceeding with the checkout.
     * Checkout Page: If all necessary information is present, the method
     * prepares the checkout page, including the customer’s shopping cart and total items.
     * @param principal
     * @param model
     * @return "checkout"
     */
    @GetMapping("/check-out")
    public String checkOut(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.getCustomer(principal.getName());
            if (customer.getAddress() == null || customer.getCity() == null || customer.getPhoneNumber() == null) {
                model.addAttribute("information", "You need update your information before check out");
                List<Country> countryList = countryService.findAll();
                List<City> cities = cityService.findAll();
                model.addAttribute("customer", customer);
                model.addAttribute("cities", cities);
                model.addAttribute("countries", countryList);
                model.addAttribute("title", "Profile");
                model.addAttribute("page", "Profile");
                return "customer-information";
            } else {
                ShoppingCart cart = customerService.findByUsername(principal.getName()).getCart();
                model.addAttribute("customer", customer);
                model.addAttribute("title", "Check-Out");
                model.addAttribute("page", "Check-Out");
                model.addAttribute("shoppingCart", cart);
                model.addAttribute("grandTotal", cart.getTotalItems());
                return "checkout";
            }
        }
    }

    /**
     * Retrieves and displays a list of orders associated
     * with the authenticated customer.
     * Authentication Check: Ensures the user is logged in.
     * Order List: The customer’s orders are retrieved and
     * added to the model for display on the "Order" page.
     * @param model
     * @param principal
     * @return "order"
     */
    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            List<Order> orderList = customer.getOrders();
            model.addAttribute("orders", orderList);
            model.addAttribute("title", "Order");
            model.addAttribute("page", "Order");
            return "order";
        }
    }

    /**
     * Displays the details of a specific order, identified by its orderId.
     * Authentication Check: Ensures the user is logged in.
     * Order Retrieval: The specific order is retrieved using the orderId, and
     * its details are added to the model for display on the "Order Detail" page.
     * @param orderId
     * @param model
     * @param principal
     * @return will show the specific order
     */
    @GetMapping("/orders/specific-order-detail/{orderId}")
    public String getOrderDetails(@PathVariable("orderId") Long orderId, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Order order = orderService.getOrderById(orderId); // Retrieve the specific order by its ID

            model.addAttribute("order", order);
            model.addAttribute("title", "Order Detail");
            model.addAttribute("page", "Order Detail");
            model.addAttribute("success", "Order details retrieved successfully");
            return "specific-order-detail";
        }
    }


    /**
     * Cancels an order identified by its id.
     * Method Mapping: This endpoint can handle both PUT and GET requests.
     * Order Cancellation: The order is canceled through the orderService, and a success message is flashed to the user.
     * Redirection: The user is redirected back to the orders list page.
     * @param id
     * @param attributes
     * @return true if the order was canceled successfully, false otherwise
     */
    @RequestMapping(value = "/cancel-order", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(Long id, RedirectAttributes attributes) {
        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Cancel order successfully!");
        return "redirect:/orders";
    }


    /**
     * Handles the creation of a new order from the customer’s shopping cart.
     * Authentication Check: Ensures the user is logged in.
     * Order Creation: The customer’s shopping cart is converted into an order,
     * which is then saved. The total items in the session are cleared to reflect
     * the completed purchase.
     * Order Confirmation: After the order is created, the user is directed to the
     * "Order Detail" page to view the newly created order.
     * @param principal
     * @param model
     * @param session
     * @return "order-detail"
     */
    @RequestMapping(value = "/add-order", method = {RequestMethod.POST})
    public String createOrder(Principal principal,
                              Model model,
                              HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getCart();
            Order order = orderService.save(cart);
            session.removeAttribute("totalItems");
            model.addAttribute("order", order);
            model.addAttribute("title", "Order Detail");
            model.addAttribute("page", "Order Detail");
            model.addAttribute("success", "Add order successfully");
            return "order-detail";
        }
    }
}
