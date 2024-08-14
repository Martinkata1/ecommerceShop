package com.ecommerce.customer.config;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.service.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

/**
 * The CustomerServiceConfig class implements the UserDetailsService
 * interface from Spring Security and provides a mechanism to load
 * user details based on username. This class is key to the
 * authentication process in Spring Security because it retrieves
 * the user information from the database and adapts it to the format
 * expected by Spring Security.
 */
public class CustomerServiceConfig implements UserDetailsService {
    /**
     * It injects a dependency to the CustomerRepository,
     * which is an interface for accessing data related to users.
     * The repository provides methods to search for users in the database.
     */
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * This method loads a user by their username.
     * User Lookup: The method uses the customerRepository to find the
     * user in the database based on the supplied username.
     * Handling missing user: If the user is not found, a
     * UsernameNotFoundException exception is thrown.
     * Create UserDetails: If the user is found, the method
     * creates a new User object that implements UserDetails.
     * This object contains the user's username, password, and roles,
     * converted to a list by SimpleGrantedAuthority.
     * @param username
     * @return the user
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("Could not find username");
        }
        return new User(customer.getUsername(),
                customer.getPassword(),
                customer.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
    private CustomerMapper customerMapper;

    public CustomerDto getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return customerMapper.toDto(customer);
    }

    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

}
