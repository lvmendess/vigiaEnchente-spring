package com.vigiaenchente.notifier.model;

import com.vigiaenchente.core.domain.entity.PushSubscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private Long id;
    private String endpoint;
    private String p256dh;
    private String auth;
    private Long userId;
    private String payload;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Subscription fromEntity(PushSubscription entity) {
        Subscription subscription = new Subscription();
        subscription.setId(entity.getId());
        subscription.setEndpoint(entity.getEndpoint());
        subscription.setP256dh(entity.getP256dh());
        subscription.setAuth(entity.getAuth());
        subscription.setUserId(entity.getUserId());
        subscription.setPayload(entity.getPayload());
        subscription.setCreatedAt(entity.getCreatedAt());
        subscription.setUpdatedAt(entity.getUpdatedAt());
        return subscription;
    }

    public NotificationPayload getPayloadObject() {
        return NotificationPayload.fromJson(this.payload);
    }
}
