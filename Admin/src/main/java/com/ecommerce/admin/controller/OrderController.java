package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.service.OrderService;
import lombok.RequiredArgsConstructor;
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
 * This controller is responsible for managing orders in the application.
 */
@Controller
@RequiredArgsConstructor
public class OrderController {
    /**
     * A service layer that manages the business logic for orders.
     */
    private final OrderService orderService;

    /**
     * Handles GET requests to /orders.
     * Checks if the user is authenticated (Principal is not null). If not,
     * redirects the user to the login page.
     * Calls findALlOrders() method of OrderService
     * to get a list of orders and adds it to the model.
     * @param model
     * @param principal
     * @return "orders"
     */
    @GetMapping("/orders")
    public String getAll(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            List<Order> orderList = orderService.findALlOrders();
            model.addAttribute("orders", orderList);
            return "orders";
        }
    }

    /**
     * Handles GET requests to /order/order-details/{orderId} where {orderId} is the order ID.
     * Checks if the user is authenticated. If not, redirects to login page.
     * Calls getOrderById(orderId) method of OrderService to get the order details.
     * @param orderId
     * @param model
     * @param principal
     * @return specific order details
     */
    @GetMapping("/order/order-details/{orderId}")///{orderId}
    public String getOrderDetails(@PathVariable("orderId") Long orderId, Model model, Principal principal) {
        // Check if user is logged in
        if (principal == null) {
            return "redirect:/login";
        }

        // Retrieve order details based on orderId
        Order order = orderService.getOrderById(orderId);

        // Add order details to the model
        model.addAttribute("order", order);
        model.addAttribute("title", "Order Details");

        return "order-details";
    }

    /**
     * Handles PUT and GET requests to /accept-order to accept an order with a given ID.
     * Checks if the user is authenticated. If not, redirects to login page.
     * Calls the acceptOrder(id) method of the OrderService to mark the order as accepted.
     * Adds a success message to the RedirectAttributes and redirects back to the order list page.
     * @param id
     * @param attributes
     * @param principal
     * @return "redirect:/orders"
     */
    @RequestMapping(value = "/accept-order", method = {RequestMethod.PUT, RequestMethod.GET})
    public String acceptOrder(Long id, RedirectAttributes attributes, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            orderService.acceptOrder(id);
            attributes.addFlashAttribute("success", "Order Accepted");
            return "redirect:/orders";
        }
    }

    /**
     * Handles PUT and GET requests to /cancel-order to cancel an order with a given ID.
     * Checks if the user is authenticated. If not, redirects to login page.
     * Calls the cancelOrder(id) method of the OrderService to mark the order as canceled.
     * Redirects back to the order list page.
     * @param id
     * @param principal
     * @return "redirect:/orders"
     */
    @RequestMapping(value = "/cancel-order", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            orderService.cancelOrder(id);
            return "redirect:/orders";
        }
    }


}
