package com.vigiaenchente.notifier.service;

import com.vigiaenchente.core.domain.entity.PushSubscription;
import com.vigiaenchente.notifier.model.NotificationPayload;
import com.vigiaenchente.notifier.model.Subscription;
import com.vigiaenchente.notifier.repository.SubscriptionRepository;
import com.vigiaenchente.notifier.service.PushNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

    private final PushNotificationService pushNotificationService;
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Sends periodic notifications every minute
     * This sends stored notifications to all subscriptions
     */
    @Scheduled(fixedRate = 60000) // 60 seconds
    public void sendPeriodicNotifications() {
        log.info("Starting periodic notification job");

        // CORREÇÃO: Converter List<PushSubscription> para List<Subscription>
        List<Subscription> subscriptions = subscriptionRepository.findAll()
                .stream()
                .map(Subscription::fromEntity) // Usando o método estático que você já tem
                .collect(Collectors.toList());

        if (subscriptions.isEmpty()) {
            log.debug("No subscriptions to send notifications to");
            return;
        }

        log.info("Sending notifications to {} subscriptions", subscriptions.size());

        subscriptions.forEach(subscription -> {
            try {
                NotificationPayload payload = subscription.getPayloadObject();

                if (payload == null) {
                    payload = NotificationPayload.builder()
                            .title("VigiaEnchente")
                            .body("Alerta padrão")
                            .build();
                }

                pushNotificationService.sendNotification(subscription, payload);
            } catch (Exception e) {
                log.error("Error sending periodic notification to: {}",
                        subscription.getEndpoint(), e);
            }
        });

        log.info("Periodic notification job completed");
    }
}