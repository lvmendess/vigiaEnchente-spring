package com.vigiaenchente.notifier.service;

import com.vigiaenchente.notifier.model.NotificationPayload;
import com.vigiaenchente.notifier.model.Subscription;
import com.vigiaenchente.notifier.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        Subscription subscription = subscriptionRepository.findByEndpoint(endpoint)
                .orElse(new Subscription());

        subscription.setEndpoint(endpoint);
        subscription.setP256dh(p256dh);
        subscription.setAuth(auth);
        subscription.setUserId(userId);
        subscription.setPayload(payload.toJson());
        subscription.setUpdatedAt(LocalDateTime.now());

        if (subscription.getId() == null) {
            subscription.setCreatedAt(LocalDateTime.now());
        }

        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void unsubscribe(String endpoint) {
        subscriptionRepository.findByEndpoint(endpoint)
                .ifPresent(subscriptionRepository::delete);
    }

    @Transactional(readOnly = true)
    public boolean isSubscribed(String endpoint) {
        return subscriptionRepository.existsByEndpoint(endpoint);
    }
}
