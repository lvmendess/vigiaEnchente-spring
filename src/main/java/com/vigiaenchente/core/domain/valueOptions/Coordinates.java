package com.vigiaenchente.core.domain.valueOptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    private Double latitude;
    private Double longitude;

    public boolean isValid() {
        return latitude != null && longitude != null
                && latitude >= -90 && latitude <= 90
                && longitude >= -180 && longitude <= 180;
    }
}
