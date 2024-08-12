package com.ecommerce.admin.config;

import com.ecommerce.library.model.Admin;
import com.ecommerce.library.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The AdminDetails class implements the UserDetails interface from Spring Security
 * and serves to adapt the Admin model to Spring's security system.
 * This is important because Spring Security uses UserDetails to retrieve
 * information about users during authentication and authorization.
 */
public class AdminDetails implements UserDetails {
    /**
     * information about the Admin
     */
    private Admin admin;

    /**
     * For each admin role stored in a list in admin, a new
     * SimpleGrantedAuthority is created and added to the authorities list.
     * This allows Spring Security to check what rights the
     * user has and whether they can access certain resources.
     * @return GrantedAuthority collection that represents the user's authorities (roles)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : admin.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    /**
     * @return admin's password it is for authentication purpose
     */
    @Override
    public String getPassword() {
        return admin.getPassword();
    }
    /**
     * @return admin's username it is for authentication purpose
     */
    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    /**
     * @return account is  always active
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * @return account is  always unlocked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * @return account is  always active for credentials
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return account is  active
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
