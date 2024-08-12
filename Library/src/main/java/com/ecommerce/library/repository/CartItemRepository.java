package com.ecommerce.library.repository;

import com.ecommerce.library.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * extends JpaRepository<CartItem, Long>: This means that CartItemRepository extends
 * JpaRepository, which provides a wide range of CRUD (Create, Read, Update, Delete)
 * operations, pagination, and sorting capabilities for CartItem entities.
 * JpaRepository is a part of Spring Data JPA and simplifies the implementation of data access layers.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     * @Query: This annotation is used to define custom queries. In this case, the query
     * is a native SQL query.
     * value: The SQL query string. This query updates the cart_items table, setting the shopping_cart_id
     * to null for all rows where shopping_cart_id matches the given parameter (cartId).
     * nativeQuery = true: Indicates that this query is a native SQL query rather than JPQL
     * (Java Persistence Query Language). JPQL is a query language specific to JPA, but in this case, a native SQL query is used directly.
     * void deleteCartItemById(Long cartId): This method executes the provided native SQL query. It does not return any results; it only performs the update operation.
     * @param cartId
     */
    @Query(value = "update cart_items set shopping_cart_id = null where shopping_cart_id = ?1", nativeQuery = true)
    void deleteCartItemById(Long cartId);
}
