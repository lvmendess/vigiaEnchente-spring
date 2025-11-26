package com.vigiaenchente.api.repository;

import com.vigiaenchente.core.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    /**
     * Find address by user ID
     */
    @Query("SELECT a FROM Address a WHERE a.user.id = :userId")
    Optional<Address> findByUserId(@Param("userId") Integer userId);

    /**
     * Find all addresses in a specific city
     */
    List<Address> findByCidade(String cidade);

    /**
     * Find all addresses in a specific CEP
     */
    List<Address> findByCep(String cep);

    /**
     * Delete address by user ID
     */
    @Query("DELETE FROM Address a WHERE a.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
}
