package com.ecommerce.library.service;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;

/**
 * Service for Admin
 * Save or update the admin's entity
 * takes the string username and
 * representing the username of the admin to be found
 */

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);
}
