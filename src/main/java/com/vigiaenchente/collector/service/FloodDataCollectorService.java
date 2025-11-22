package com.vigiaenchente.collector.service;

import com.vigiaenchente.collector.client.FloodApiClient;
import com.vigiaenchente.collector.dto.FloodDataResponse;
import com.vigiaenchente.core.domain.valueOptions.Coordinates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FloodDataCollectorService {

    private final FloodApiClient floodApiClient;

    @Cacheable(value = "floodData", key = "#coordinates.latitude + ',' + #coordinates.longitude")
    public List<Double> getFloodData(Coordinates coordinates) {
        try {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(2);

            log.info("Fetching flood data for coordinates: {}", coordinates);

            FloodDataResponse response = floodApiClient.getFloodData(
                    coordinates.getLatitude(),
                    coordinates.getLongitude(),
                    startDate,
                    endDate
            );

            return processFloodData(response);
        } catch (Exception e) {
            log.error("Error fetching flood data for coordinates: {}", coordinates, e);
            return List.of();
        }
    }

    private List<Double> processFloodData(FloodDataResponse response) {
        if (response == null || response.getDaily() == null
                || response.getDaily().getRiverDischarge() == null) {
            return List.of();
        }

        List<Double> dischargeData = response.getDaily().getRiverDischarge();
        return dischargeData.size() >= 3
                ? dischargeData.subList(0, 3)
                : dischargeData;
    }
}

