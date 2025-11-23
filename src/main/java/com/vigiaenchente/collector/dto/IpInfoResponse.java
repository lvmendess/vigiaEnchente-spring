package com.vigiaenchente.collector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpInfoResponse {
    private String ip;
    private String city;
    private String region;
    private String country;
    private String loc; // "latitude,longitude"
    private String org;
    private String postal;
    private String timezone;
}