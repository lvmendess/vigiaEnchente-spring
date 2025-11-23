package com.vigiaenchente.collector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FloodDataResponse {

    @JsonProperty("daily")
    private DailyData daily;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DailyData {

        @JsonProperty("time")
        private List<String> time;

        @JsonProperty("river_discharge")
        private List<Double> riverDischarge;
    }
}
