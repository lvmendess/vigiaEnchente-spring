package com.vigiaenchente.collector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCodingResponse {
    private String name;
    private Double lat;
    private Double lon;
    private String country;
    private String state;
}
