package com.vigiaenchente.notifier.service;

import com.vigiaenchente.core.domain.entity.PushSubscription;
import com.vigiaenchente.notifier.model.NotificationPayload;
import com.vigiaenchente.notifier.model.Subscription;
import com.vigiaenchente.notifier.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public Subscription saveOrUpdateSubscription(
            String endpoint,
            String p256dh,
            String auth,
            Long userId,
            NotificationPayload payload) {

        PushSubscription entity = subscriptionRepository.findByEndpoint(endpoint)
                .orElse(new PushSubscription());

        entity.setEndpoint(endpoint);
        entity.setP256dh(p256dh);
        entity.setAuth(auth);
        entity.setUserId(userId);
        entity.setPayload(payload.toJson());

        if (entity.getId() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        entity.setUpdatedAt(LocalDateTime.now());

        PushSubscription saved = subscriptionRepository.save(entity);
        return Subscription.fromEntity(saved);
    }

    @Transactional
    public void unsubscribe(String endpoint) {
        subscriptionRepository.findByEndpoint(endpoint)
                .ifPresent(subscriptionRepository::delete);
        log.info("Subscription removed for endpoint: {}", endpoint);
    }

    @Transactional(readOnly = true)
    public boolean isSubscribed(String endpoint) {
        return subscriptionRepository.existsByEndpoint(endpoint);
    }

    @Transactional(readOnly = true)
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(Subscription::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(Subscription::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cleanupOldSubscriptions() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(90);
        subscriptionRepository.deleteOldSubscriptions(cutoffDate);
        log.info("Cleaned up old subscriptions before: {}", cutoffDate);
    }
}