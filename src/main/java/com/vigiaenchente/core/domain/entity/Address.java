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
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_address_user", nullable = false)
    private User user;

    @Column(nullable = false)
    private String rua;

    @Column(name = "num_rua")
    private String numRua;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;
}