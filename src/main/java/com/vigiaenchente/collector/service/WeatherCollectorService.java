package com.vigiaenchente.collector.service;

import com.vigiaenchente.collector.client.OpenWeatherClient;
import com.vigiaenchente.collector.dto.WeatherResponse;
import com.vigiaenchente.core.domain.valueOptions.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherCollectorService {

    private final OpenWeatherClient weatherClient;

    @Cacheable(value = "weather", key = "#city", unless = "#result == null")
    public WeatherData getWeatherByCity(String city) {
        try {
            log.info("Fetching weather data for city: {}", city);
            WeatherResponse response = weatherClient.getWeatherByCity(city);
            return mapToWeatherData(response);
        } catch (Exception e) {
            log.error("Error fetching weather for city: {}", city, e);
            throw new RuntimeException("Falha ao obter dados climáticos", e);
        }
    }

    @Cacheable(value = "weather", key = "#lat + ',' + #lon", unless = "#result == null")
    public WeatherData getWeatherByCoordinates(Double lat, Double lon) {
        try {
            log.info("Fetching weather data for coordinates: {}, {}", lat, lon);
            WeatherResponse response = weatherClient.getWeatherByCoordinates(lat, lon);
            return mapToWeatherData(response);
        } catch (Exception e) {
            log.error("Error fetching weather for coordinates: {}, {}", lat, lon, e);
            throw new RuntimeException("Falha ao obter dados climáticos", e);
        }
    }

    private WeatherData mapToWeatherData(WeatherResponse response) {
        return WeatherData.builder()
                .cityName(response.getName())
                .temperature(response.getMain().getTemp())
                .tempMax(response.getMain().getTempMax())
                .tempMin(response.getMain().getTempMin())
                .humidity(response.getMain().getHumidity())
                .description(response.getWeather().get(0).getDescription())
                .icon(response.getWeather().get(0).getIcon())
                .windSpeed(response.getWind().getSpeed())
                .build();
    }
}
