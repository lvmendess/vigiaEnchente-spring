package com.vigiaenchente.notifier.controller;

import com.vigiaenchente.notifier.dto.SubscriptionRequest;
import com.vigiaenchente.notifier.model.NotificationPayload;
import com.vigiaenchente.notifier.model.Subscription;
import com.vigiaenchente.notifier.service.PushNotificationService;
import com.vigiaenchente.notifier.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "true")
@Slf4j
public class NotificationController {

    private final SubscriptionService subscriptionService;
    private final PushNotificationService pushNotificationService;

    @PostMapping("/subscribe")
    public ResponseEntity<Map<String, Object>> subscribe(
            @Valid @RequestBody SubscriptionRequest request,
            HttpSession session) {

        log.info("Received subscription request");
        log.debug("Subscription details: {}", request);

        Integer userId = (Integer) session.getAttribute("userId");

        var subscriptionKeys = request.getSubscription();
        NotificationPayload payload = request.getPayload();

        if (payload == null) {
            payload = NotificationPayload.builder()
                    .title("VigiaEnchente")
                    .body("Notificação padrão")
                    .build();
        }

        Subscription subscription = subscriptionService.saveOrUpdateSubscription(
                subscriptionKeys.getEndpoint(),
                subscriptionKeys.getKeys().getP256dh(),
                subscriptionKeys.getKeys().getAuth(),
                userId,
                payload
        );

        log.info("Subscription saved successfully for endpoint: {}", subscription.getEndpoint());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "success", true,
                        "saved", true,
                        "message", "Subscription saved successfully"
                ));
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Map<String, Object>> unsubscribe(
            @RequestParam String endpoint) {

        subscriptionService.unsubscribe(endpoint);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Unsubscribed successfully"
        ));
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendNotification(
            @RequestBody NotificationPayload payload) {

        pushNotificationService.sendNotificationToAll(payload);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Notifications sent"
        ));
    }
}
