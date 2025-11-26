package com.vigiaenchente.api.dto;

import com.vigiaenchente.core.domain.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String rua;
    private String numRua;
    private String cep;
    private String bairro;
    private String cidade;

    public static AddressDTO fromEntity(Address address) {
        if (address == null) {
            return null;
        }

        return AddressDTO.builder()
                .rua(address.getStreet())
                .numRua(address.getNumber())
                .cep(address.getCep())
                .bairro(address.getNeighborhood())
                .cidade(address.getCity())
                .build();
    }
}
