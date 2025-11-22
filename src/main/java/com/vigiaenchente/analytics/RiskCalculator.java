package com.vigiaenchente.analytics;

import com.vigiaenchente.core.domain.enums.FloodRiskLevel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RiskCalculator {

    private static final double LOW_THRESHOLD = 5.0;
    private static final double MEDIUM_THRESHOLD = 10.0;
    private static final double HIGH_VARIATION_THRESHOLD = 3.0;

    /**
     * Calculates the flood risk level based on river discharge data.
     *
     * Algorithm:
     * - Calculates average discharge over 3 days
     * - Calculates variation (day 3 - day 1)
     * - Determines risk level based on thresholds:
     *   * avg < 5: LOW (unless variation > 3, then MEDIUM)
     *   * avg < 10: MEDIUM (unless variation > 3, then HIGH)
     *   * avg >= 10: HIGH
     */
    public FloodRiskLevel calculateRiskLevel(List<Double> dischargeData) {
        if (dischargeData == null || dischargeData.size() < 3) {
            return FloodRiskLevel.BAIXO;
        }

        double average = calculateAverage(dischargeData);
        double variation = calculateVariation(dischargeData);

        if (average < LOW_THRESHOLD) {
            return variation > HIGH_VARIATION_THRESHOLD
                    ? FloodRiskLevel.MEDIO
                    : FloodRiskLevel.BAIXO;
        }

        if (average < MEDIUM_THRESHOLD) {
            return variation > HIGH_VARIATION_THRESHOLD
                    ? FloodRiskLevel.ALTO
                    : FloodRiskLevel.MEDIO;
        }

        return FloodRiskLevel.ALTO;
    }

    public double calculateAverage(List<Double> data) {
        if (data == null || data.isEmpty()) {
            return 0.0;
        }

        double sum = data.stream()
                .limit(3)
                .mapToDouble(Double::doubleValue)
                .sum();

        return sum / Math.min(data.size(), 3);
    }

    public double calculateVariation(List<Double> data) {
        if (data == null || data.size() < 3) {
            return 0.0;
        }

        return data.get(2) - data.get(0);
    }

    public String getRecommendation(FloodRiskLevel riskLevel) {
        return switch (riskLevel) {
            case BAIXO -> "Não há risco de enchente. Condições normais.";
            case MEDIO -> "Risco médio de enchente. Mantenha-se atento aos alertas.";
            case ALTO -> "Risco alto de enchente! Siga as orientações das autoridades locais.";
        };
    }
}
