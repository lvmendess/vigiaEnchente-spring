package com.vigiaenchente.notifier.model;

import com.vigiaenchente.core.domain.entity.PushSubscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private Integer id;
    private String endpoint;
    private String p256dh;
    private String auth;
    private Integer userId;
    private String payload;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Subscription fromEntity(PushSubscription entity) {
        if (entity == null) {
            return null;
        }

        return Subscription.builder()
                .id(entity.getId())
                .endpoint(entity.getEndpoint())
                .p256dh(entity.getP256dh())
                .auth(entity.getAuth())
                .userId(entity.getUserId())
                .payload(entity.getPayload())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public NotificationPayload getPayloadObject() {
        return NotificationPayload.fromJson(this.payload);
    }
}
