package com.vigiaenchente.api.repository;

import com.vigiaenchente.core.domain.entity.PushSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<PushSubscription, Long> {

    /**
     * Find subscription by endpoint
     */
    Optional<PushSubscription> findByEndpoint(String endpoint);

    /**
     * Find all subscriptions for a specific user
     */
    List<PushSubscription> findByUserId(Long userId);

    /**
     * Check if endpoint exists
     */
    boolean existsByEndpoint(String endpoint);

    /**
     * Delete subscription by endpoint
     */
    void deleteByEndpoint(String endpoint);

    /**
     * Find all active subscriptions (updated in the last 30 days)
     */
    @Query("SELECT s FROM PushSubscription s WHERE s.updatedAt >= :cutoffDate")
    List<PushSubscription> findActiveSubscriptions(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Delete old subscriptions (not updated in the last 90 days)
     */
    @Modifying
    @Query("DELETE FROM PushSubscription s WHERE s.updatedAt < :cutoffDate")
    void deleteOldSubscriptions(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Count subscriptions by user
     */
    long countByUserId(Long userId);
}
