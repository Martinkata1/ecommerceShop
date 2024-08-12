package com.ecommerce.library.repository;

import com.ecommerce.library.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository: Marks the interface as a Spring Data repository.
 * JpaRepository: Provides a set of methods for data access operations.
 * It get from model.
 * Custom Query Method: The method findByUsername is automatically
 * implemented to perform a query based on the method name.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
