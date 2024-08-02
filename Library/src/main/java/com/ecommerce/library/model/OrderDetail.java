package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    /*public int getQuantity() {
        return product.getQuantity();
    }

    public double getPrice() {
        return product.getPrice();
    }*/

    /*
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "quantity", referencedColumnName = "quantity")
    private int quantity;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "price", referencedColumnName = "cost_price")
    private double price;

    public double getPrice() {
        if (product != null) {
            return product.getCostPrice();
        } else {
            // Обработка на случаите, когато product е null
            return 0.0; // или друга подходяща стойност
        }
    }*/


}
