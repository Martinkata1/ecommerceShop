package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller for shopping cart
 */
@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    /**
     * The controller interacts with the ShoppingCartService,
     * ProductService, and CustomerService to handle these operations.
     */
    @Autowired
    private  ShoppingCartService cartService;
    @Autowired
    private  ProductService productService;
    @Autowired
    private  CustomerService customerService;

    /**
     * Displays the shopping cart page for the logged-in customer.
     * Cart and Customer Data: Retrieves the shopping cart associated with the current customer and calculates the total price. The cart data is then displayed on the "cart" view.
     * Session Management: Updates the session with the total number of items in the cart.
     * @param model
     * @param principal
     * @param session
     * @return "cart"
     */
    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getCart();
        if (cart == null) {
            model.addAttribute("check");

        }
        if (cart != null) {
            model.addAttribute("grandTotal", cart.getTotalPrice());
        }
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("title", "Cart");
        session.setAttribute("totalItems", cart.getTotalItems());
        return "cart";


    }

    /**
     * Adds a specified product to the shopping cart. If the user is not logged in, they are redirected to the login page.
     * Product and Quantity: The method receives the product ID and quantity, adds the product to the cart, and updates the session with the new total items count.
     * Redirection: After adding the item, the user is redirected back to the page they were on.
     * @param id
     * @param quantity
     * @param request
     * @param model
     * @param principal
     * @param session
     * @return will redirect to referer
     */
    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {


        ProductDto productDto = productService.getById(id);
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");
    }

    /**
     * Updates the quantity of a product in the shopping cart.
     * Product and Quantity Update: The method retrieves the product and updates its quantity in the cart. The session is updated with the new total items count.
     * Redirection: The user is redirected to the cart page after the update.
     * @param id
     * @param quantity
     * @param model
     * @param principal
     * @param session
     * @return "cart"
     */
    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
                             @RequestParam("quantity") int quantity,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        ProductDto productDto = productService.getById(id);
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.updateCart(productDto, quantity, username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/cart";

    }

    /**
     * Removes a specific product from the shopping cart.
     * Product Removal: The method retrieves the product by its ID and removes it from the cart. The session is updated with the new total items count.
     * Redirection: After removing the item, the user is redirected to the cart page.
     * @param id
     * @param model
     * @param principal
     * @param session
     * @return "cart" with deleted item
     */
    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long id,
                             Model model,
                             Principal principal,
                             HttpSession session
    ) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = cartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            return "redirect:/cart";
        }
    }

}
