package com.vigiaenchente.core.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_address_user", nullable = false)
    private User user;

    @Column(name = "rua", nullable = false)
    private String street;

    @Column(name = "num_rua")
    private String number;

    @Column(name = "cep", nullable = false, length = 8)
    private String zipCode;

    @Column(name = "bairro", nullable = false)
    private String neighborhood;

    @Column(name = "cidade", nullable = false)
    private String city;
}