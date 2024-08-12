package com.ecommerce.library.service;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;

import java.util.List;

/**
 * Service for Oder
 * Save
 * find all by username, all orders
 * accept and cancel orders
 * get order by id
 */
public interface OrderService {
    Order save(ShoppingCart shoppingCart);

    List<Order> findAll(String username);

    List<Order> findALlOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);

    Order getOrderById(Long id);


    //Order findById(Long orderId);

}
