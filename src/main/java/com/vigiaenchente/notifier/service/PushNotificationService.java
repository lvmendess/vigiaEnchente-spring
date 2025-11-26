package com.vigiaenchente.notifier.service;

import com.vigiaenchente.core.domain.entity.PushSubscription;
import com.vigiaenchente.notifier.model.NotificationPayload;
import com.vigiaenchente.notifier.model.Subscription;
import com.vigiaenchente.notifier.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Notification;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushNotificationService {

    private final PushService pushService;
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Sends a push notification to a specific subscription
     */
    public boolean sendNotification(Subscription subscription, NotificationPayload payload) {
        try {
            Notification notification = new Notification(
                    subscription.getEndpoint(),
                    subscription.getP256dh(),
                    subscription.getAuth(),
                    payload.toJson().getBytes()
            );

            pushService.send(notification);
            log.info("Notification sent successfully to: {}", subscription.getEndpoint());
            return true;

        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException e) {
            log.error("Error sending notification to: {}", subscription.getEndpoint(), e);
            handleFailedNotification(subscription, e);
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Notification sending interrupted", e);
            return false;
        }
    }

    /**
     * Sends notifications to all active subscriptions
     */
    /**
     * Sends notifications to all active subscriptions
     */
    public void sendNotificationToAll(NotificationPayload payload) {
        List<PushSubscription> subscriptions = subscriptionRepository.findAll();

        log.info("Sending notifications to {} subscriptions", subscriptions.size());

        subscriptions.forEach(entity -> {
            Subscription subscription = Subscription.fromEntity(entity);
            sendNotification(subscription, payload);
        });
    }

    /**
     * Sends notifications to all subscriptions of a specific user
     */
    public void sendNotificationToUser(Integer userId, NotificationPayload payload) {
        List<PushSubscription> subscriptions = subscriptionRepository.findByUserId(userId);

        log.info("Sending notifications to user {} ({} subscriptions)", userId, subscriptions.size());

        subscriptions.forEach(entity -> {
            Subscription subscription = Subscription.fromEntity(entity);
            sendNotification(subscription, payload);
        });
    }


    private void handleFailedNotification(Subscription subscription, Exception e) {
        // Check if subscription is invalid (410 Gone or 404 Not Found)
        String errorMessage = e.getMessage();
        if (errorMessage != null && (errorMessage.contains("410") || errorMessage.contains("404"))) {
            log.warn("Removing invalid subscription: {}", subscription.getEndpoint());
            subscriptionRepository.deleteByEndpoint(subscription.getEndpoint());
        }
    }
}
