package com.vigiaenchente.collector.service;

import com.vigiaenchente.collector.client.IpInfoClient;
import com.vigiaenchente.collector.client.OpenWeatherClient;
import com.vigiaenchente.collector.dto.IpInfoResponse;
import com.vigiaenchente.collector.dto.GeoCodingResponse;
import com.vigiaenchente.core.domain.valueOptions.Coordinates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeolocationService {

    private static final String DEFAULT_CITY = "Sabará";
    private static final Coordinates DEFAULT_COORDINATES = new Coordinates(-19.8949, -43.8148);

    private final IpInfoClient ipInfoClient;
    private final OpenWeatherClient weatherClient;

    @Cacheable("cityByIp")
    public String getCityByIp() {
        try {
            IpInfoResponse response = ipInfoClient.getIpInfo();
            return response.getCity() != null ? response.getCity() : DEFAULT_CITY;
        } catch (Exception e) {
            log.error("Error getting city by IP", e);
            return DEFAULT_CITY;
        }
    }

    @Cacheable(value = "coordinatesByCity", key = "#city")
    public Coordinates getCoordinatesByCity(String city) {
        try {
            List<GeoCodingResponse> response = weatherClient.getCoordinatesByCity(city);

            if (response != null && !response.isEmpty()) {
                GeoCodingResponse location = response.get(0);
                return new Coordinates(location.getLat(), location.getLon());
            }

            return getCoordinatesByIp();
        } catch (Exception e) {
            log.error("Error getting coordinates for city: {}", city, e);
            return getCoordinatesByIp();
        }
    }

    @Cacheable("coordinatesByIp")
    public Coordinates getCoordinatesByIp() {
        try {
            IpInfoResponse response = ipInfoClient.getIpInfo();

            if (response.getLoc() != null) {
                String[] parts = response.getLoc().split(",");
                if (parts.length == 2) {
                    double lat = Double.parseDouble(parts[0].trim());
                    double lon = Double.parseDouble(parts[1].trim());
                    return new Coordinates(lat, lon);
                }
            }
        } catch (Exception e) {
            log.error("Error getting coordinates by IP", e);
        }

        return DEFAULT_COORDINATES;
    }
}
