package com.vigiaenchente.analytics.model;

import com.vigiaenchente.core.domain.enums.FloodRiskLevel;
import com.vigiaenchente.core.domain.valueOptions.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskAnalysis {
    private Coordinates coordinates;
    private FloodRiskLevel riskLevel;
    private List<Double> riverDischargeData;
    private Double average;
    private Double variation;
    private String recommendation;

    @Builder.Default
    private LocalDateTime analysisTime = LocalDateTime.now();

    public String getRiskLevelCode() {
        return riskLevel != null ? riskLevel.getCode() : "baixo";
    }

    public String getRiskLevelDescription() {
        return riskLevel != null ? riskLevel.getDescription() : "Unknown";
    }
}


