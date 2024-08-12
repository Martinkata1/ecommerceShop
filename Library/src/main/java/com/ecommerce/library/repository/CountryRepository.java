package com.ecommerce.library.repository;

import com.ecommerce.library.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Country
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
