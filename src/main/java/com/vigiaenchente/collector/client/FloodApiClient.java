package com.vigiaenchente.collector.client;

import com.vigiaenchente.collector.dto.FloodDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class FloodApiClient {

    private final RestTemplate restTemplate;

    @Value("${floodapi.base-url}")
    private String baseUrl;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FloodDataResponse getFloodData(Double latitude, Double longitude,
                                          LocalDate startDate, LocalDate endDate) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/flood")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("daily", "river_discharge")
                .queryParam("models", "forecast_v4")
                .queryParam("start_date", startDate.format(DATE_FORMATTER))
                .queryParam("end_date", endDate.format(DATE_FORMATTER))
                .toUriString();

        return restTemplate.getForObject(url, FloodDataResponse.class);
    }
}

