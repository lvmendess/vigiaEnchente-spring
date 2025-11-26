package com.vigiaenchente.api.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    private String name;
    private String email;
    private String phone;
    private String password;
    private String address;
}
