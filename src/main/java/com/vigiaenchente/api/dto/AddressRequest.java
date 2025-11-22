package com.vigiaenchente.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressRequest {
    @NotBlank(message = "Street is required")
    private String street;

    private String num;

    @NotBlank(message = "CEP is required")
    @Pattern(regexp = "\\d{8}", message = "CEP must have 8 digits")
    private String cep;

    @NotBlank(message = "Neighborhood is required")
    private String neighbor;

    @NotBlank(message = "City is required")
    private String city;
}

