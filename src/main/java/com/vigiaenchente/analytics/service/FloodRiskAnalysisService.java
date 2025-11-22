package com.vigiaenchente.analytics.service;

import com.vigiaenchente.analytics.RiskCalculator;
import com.vigiaenchente.analytics.model.RiskAnalysis;
import com.vigiaenchente.collector.service.FloodDataCollectorService;
import com.vigiaenchente.core.domain.enums.FloodRiskLevel;
import com.vigiaenchente.core.domain.valueOptions.Coordinates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FloodRiskAnalysisService {

    private final FloodDataCollectorService floodDataCollector;
    private final RiskCalculator riskCalculator;

    @Cacheable(value = "riskAnalysis", key = "#coordinates.latitude + ',' + #coordinates.longitude")
    public RiskAnalysis analyzeFloodRisk(Coordinates coordinates) {
        try {
            log.info("Analyzing flood risk for coordinates: {}", coordinates);

            List<Double> floodData = floodDataCollector.getFloodData(coordinates);

            if (floodData == null || floodData.size() < 3) {
                log.warn("Insufficient flood data for analysis");
                return createDefaultAnalysis(coordinates);
            }

            FloodRiskLevel riskLevel = riskCalculator.calculateRiskLevel(floodData);
            String recommendation = riskCalculator.getRecommendation(riskLevel);

            return RiskAnalysis.builder()
                    .coordinates(coordinates)
                    .riskLevel(riskLevel)
                    .riverDischargeData(floodData)
                    .average(riskCalculator.calculateAverage(floodData))
                    .variation(riskCalculator.calculateVariation(floodData))
                    .recommendation(recommendation)
                    .build();

        } catch (Exception e) {
            log.error("Error analyzing flood risk for coordinates: {}", coordinates, e);
            return createDefaultAnalysis(coordinates);
        }
    }

    private RiskAnalysis createDefaultAnalysis(Coordinates coordinates) {
        return RiskAnalysis.builder()
                .coordinates(coordinates)
                .riskLevel(FloodRiskLevel.BAIXO)
                .riverDischargeData(List.of())
                .average(0.0)
                .variation(0.0)
                .recommendation("Dados insuficientes para análise")
                .build();
    }
}
