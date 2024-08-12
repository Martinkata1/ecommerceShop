package com.ecommerce.library.repository;

import com.ecommerce.library.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for City
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
