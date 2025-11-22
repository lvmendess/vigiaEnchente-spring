package com.vigiaenchente.core.domain.enums;

public enum FloodRiskLevel {
    BAIXO("baixo", "Low Risk"),
    MEDIO("medio", "Medium Risk"),
    ALTO("alto", "High Risk");

    private final String code;
    private final String description;

    FloodRiskLevel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static FloodRiskLevel fromCode(String code) {
        for (FloodRiskLevel level : values()) {
            if (level.code.equalsIgnoreCase(code)) {
                return level;
            }
        }
        return BAIXO;
    }
}