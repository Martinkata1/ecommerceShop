package com.ecommerce.admin.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customers {
    private Long id;
    private String name;
    private String username;
}
