package com.vigiaenchente.collector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private String name;

    @JsonProperty("main")
    private MainData main;

    @JsonProperty("weather")
    private List<WeatherInfo> weather;

    @JsonProperty("wind")
    private WindData wind;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainData {
        private Double temp;

        @JsonProperty("temp_max")
        private Double tempMax;

        @JsonProperty("temp_min")
        private Double tempMin;

        private Integer humidity;

        private Double pressure;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherInfo {
        private String description;
        private String icon;
        private String main;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WindData {
        private Double speed;
        private Integer deg;
    }
}

