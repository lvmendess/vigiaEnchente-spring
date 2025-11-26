package com.vigiaenchente.api.repository;

import com.vigiaenchente.core.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by phone number
     */
    Optional<User> findByPhone(String phone);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Check if phone exists
     */
    boolean existsByPhone(String phone);

    /**
     * Find user with address loaded
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.address WHERE u.id = :id")
    Optional<User> findByIdWithAddress(@Param("id") Integer id);

    /**
     * Find user by email with address loaded
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.address WHERE u.email = :email")
    Optional<User> findByEmailWithAddress(@Param("email") String email);
}