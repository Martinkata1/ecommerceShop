package com.ecommerce.admin.config;

import com.ecommerce.library.model.Admin;
import com.ecommerce.library.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

/**
 * The AdminServiceConfig class implements the UserDetailsService interface
 * from Spring Security, which is the main interface used to load user data
 * during authentication. The class retrieves the user information
 * (in this case data about administrators) from the database via the AdminRepository
 * and adapts it to the structure that Spring Security uses to manage authentication and authorization.
 */
public class AdminServiceConfig implements UserDetailsService {
    /**
     * Autowire the AdminRepository
     * Spring will take care to provide example to AdminRepository
     * at creating AdminServiceConfig
     */
    @Autowired
    private AdminRepository adminRepository;

    /**
     * This method is responsible for loading user data
     * (in this case admin data) by the supplied username.
     * Admin admin = adminRepository.findByUsername(username);
     * Retrieves the Admin object from the database via the findByUsername
     * method, which is defined in AdminRepository.
     * Checking if the administrator exists:
     * If an administrator with this username is not found,
     * a UsernameNotFoundException is thrown, indicating that
     * the user was not found.
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("Could not find username");
        }
        return new User(
                admin.getUsername(),
                admin.getPassword(),
                admin.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
