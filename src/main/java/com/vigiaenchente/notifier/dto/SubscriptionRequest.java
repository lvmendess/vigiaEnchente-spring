package com.vigiaenchente.notifier.dto;

import com.vigiaenchente.notifier.model.NotificationPayload;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionRequest {
    @NotNull(message = "Subscription is required")
    @Valid
    private SubscriptionData subscription;

    private NotificationPayload payload;

    @Data
    public static class SubscriptionData {
        @NotNull(message = "Endpoint is required")
        private String endpoint;

        @NotNull(message = "Keys are required")
        @Valid
        private Keys keys;

        @Data
        public static class Keys {
            @NotNull(message = "p256dh is required")
            private String p256dh;

            @NotNull(message = "auth is required")
            private String auth;
        }
    }
}
