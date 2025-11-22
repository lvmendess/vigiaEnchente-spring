package com.vigiaenchente.notifier.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class NotificationPayload {
    private String title;
    private String body;
    private String icon;
    private String badge;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Error converting payload to JSON", e);
            return "{\"title\":\"VigiaEnchente\",\"body\":\"Erro ao processar notificação\"}";
        }
    }

    public static NotificationPayload fromJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readValue(json, NotificationPayload.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON to payload", e);
            return null;
        }
    }
}

