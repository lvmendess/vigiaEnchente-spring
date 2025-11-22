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
public class UserProfileResponse {
    private String nome;
    private String email;
    private String phone;
    private AddressDTO endereco;

    public static UserProfileResponse fromEntity(User user) {
        return UserProfileResponse.builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .phone(user.getPhone())
                .endereco(AddressDTO.fromEntity(user.getAddress()))
                .build();
    }
}
