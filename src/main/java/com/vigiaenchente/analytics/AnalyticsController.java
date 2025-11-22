package com.vigiaenchente.analytics;

import com.vigiaenchente.analytics.model.RiskAnalysis;
import com.vigiaenchente.analytics.service.FloodRiskAnalysisService;
import com.vigiaenchente.core.domain.valueOptions.Coordinates;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final FloodRiskAnalysisService analysisService;

    @GetMapping("/risk")
    public ResponseEntity<RiskAnalysis> analyzeFloodRisk(
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        Coordinates coordinates = new Coordinates(latitude, longitude);

        if (!coordinates.isValid()) {
            return ResponseEntity.badRequest().build();
        }

        RiskAnalysis analysis = analysisService.analyzeFloodRisk(coordinates);
        return ResponseEntity.ok(analysis);
    }

    @PostMapping("/risk")
    public ResponseEntity<RiskAnalysis> analyzeFloodRiskPost(
            @RequestBody Coordinates coordinates) {

        if (!coordinates.isValid()) {
            return ResponseEntity.badRequest().build();
        }

        RiskAnalysis analysis = analysisService.analyzeFloodRisk(coordinates);
        return ResponseEntity.ok(analysis);
    }
}
