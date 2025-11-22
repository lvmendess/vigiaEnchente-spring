package com.vigiaenchente.core.domain.valueOptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {
    private String cityName;
    private Double temperature;
    private Double tempMax;
    private Double tempMin;
    private Integer humidity;
    private String description;
    private String icon;
    private Double windSpeed;
}
