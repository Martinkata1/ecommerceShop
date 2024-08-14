package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.repository.AdminRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

/**
 * Implementation of Admin service
 * Implementation for save and find by username
 */

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;


    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }
    public void initAdmin() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("1234");
        admin.setUsername("admin@abv.bg");
        adminRepository.save(admin);
    }



    public void initTest() {
        Admin test = new Admin();
        test.setUsername("testUser");
        test.setPassword("1234");
        test.setUsername("test@abv.bg");
        adminRepository.save(test);
    }
    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
