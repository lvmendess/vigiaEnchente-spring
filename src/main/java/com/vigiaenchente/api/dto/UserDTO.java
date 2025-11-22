package com.vigiaenchente.api.dto;

import com.vigiaenchente.core.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private String phone;

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}

