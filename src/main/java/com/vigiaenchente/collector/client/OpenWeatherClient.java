package com.vigiaenchente.collector.client;

import com.vigiaenchente.collector.dto.GeoCodingResponse;
import com.vigiaenchente.collector.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.base-url}")
    private String baseUrl;

    public WeatherResponse getWeatherByCity(String city) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/weather")
                .queryParam("q", city + ",br")
                .queryParam("units", "metric")
                .queryParam("lang", "pt_br")
                .queryParam("appid", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, WeatherResponse.class);
    }

    public WeatherResponse getWeatherByCoordinates(Double lat, Double lon) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/weather")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("units", "metric")
                .queryParam("lang", "pt_br")
                .queryParam("appid", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, WeatherResponse.class);
    }

    public List<GeoCodingResponse> getCoordinatesByCity(String city) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/geo/1.0/direct")
                .queryParam("q", city)
                .queryParam("limit", 1)
                .queryParam("appid", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, List.class);
    }
}
