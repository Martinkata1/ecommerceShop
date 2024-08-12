package com.ecommerce.customer.config;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Just like AdminDetails with implementation of UserDetails
 */
public class CustomerDetails implements UserDetails {
    /**
     * This field stores the user information, including the username, password, and roles.
     */
    private Customer customer;


    /**
     * his method returns a collection of GrantedAuthorities that represent the user's roles.
     * Each role from the user (customer.getRoles()) is converted to a SimpleGrantedAuthority object.
     * @return Collection of authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : customer.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    /**
     * Returns the user's password. Spring Security will use this password
     * to validate the user during the authentication process.
     * @return password
     */
    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    /**
     * Returns the username of the user. Spring Security uses this
     * username to search for and authenticate the user.
     * @return username
     */
    @Override
    public String getUsername() {
        return customer.getUsername();
    }

    /**
     * These methods return boolean values that indicate the status of
     * the user's account. They all currently return true, which means
     * the account is active, unexpired, unlocked, and with valid logins.
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
