package com.vigiaenchente.notifier.repository;

import com.vigiaenchente.core.domain.entity.PushSubscription;
import com.vigiaenchente.notifier.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Push Subscriptions
 * Handles CRUD operations for web push notification subscriptions
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<PushSubscription, Integer> {

    /**
     * Find subscription by endpoint URL
     * @param endpoint The unique endpoint URL of the subscription
     * @return Optional containing the subscription if found
     */
    Optional<PushSubscription> findByEndpoint(String endpoint);

    /**
     * Find all subscriptions for a specific user
     * @param userId The ID of the user
     * @return List of subscriptions belonging to the user
     */
    List<PushSubscription> findByUserId(Integer userId);

    /**
     * Check if an endpoint already exists
     * @param endpoint The endpoint URL to check
     * @return true if exists, false otherwise
     */
    boolean existsByEndpoint(String endpoint);

    /**
     * Delete subscription by endpoint
     * @param endpoint The endpoint URL to delete
     */
    @Transactional
    void deleteByEndpoint(String endpoint);

    /**
     * Find all active subscriptions (updated recently)
     * Subscriptions are considered active if they were updated within the specified date
     * @param cutoffDate The date threshold for considering a subscription active
     * @return List of active subscriptions
     */
    @Query("SELECT s FROM PushSubscription s WHERE s.updatedAt >= :cutoffDate ORDER BY s.updatedAt DESC")
    List<PushSubscription> findActiveSubscriptions(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Delete old subscriptions that haven't been updated recently
     * Used for cleanup of stale subscriptions
     * @param cutoffDate Subscriptions older than this date will be deleted
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PushSubscription s WHERE s.updatedAt < :cutoffDate")
    void deleteOldSubscriptions(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Count the number of subscriptions for a specific user
     * @param userId The ID of the user
     * @return Number of subscriptions
     */
    Integer countByUserId(Integer userId);

    /**
     * Find all subscriptions created within a date range
     * @param startDate Start of the date range
     * @param endDate End of the date range
     * @return List of subscriptions created in the date range
     */
    @Query("SELECT s FROM PushSubscription s WHERE s.createdAt BETWEEN :startDate AND :endDate ORDER BY s.createdAt DESC")
    List<PushSubscription> findByCreatedAtBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * Find subscriptions by user ID with pagination support
     * Useful for users with many subscriptions
     * @param userId The ID of the user
     * @return List of subscriptions ordered by most recent first
     */
    @Query("SELECT s FROM PushSubscription s WHERE s.userId = :userId ORDER BY s.updatedAt DESC")
    List<PushSubscription> findByUserIdOrderByUpdatedAtDesc(@Param("userId") Integer userId);

    /**
     * Count total active subscriptions
     * @param cutoffDate Date threshold for active subscriptions
     * @return Number of active subscriptions
     */
    @Query("SELECT COUNT(s) FROM PushSubscription s WHERE s.updatedAt >= :cutoffDate")
    long countActiveSubscriptions(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Find subscriptions without a user ID (guest subscriptions)
     * @return List of guest subscriptions
     */
    @Query("SELECT s FROM PushSubscription s WHERE s.userId IS NULL")
    List<PushSubscription> findGuestSubscriptions();

    /**
     * Delete subscriptions for a specific user
     * @param userId The ID of the user
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PushSubscription s WHERE s.userId = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

    /**
     * Update the updatedAt timestamp for a subscription
     * Used to mark a subscription as recently active
     * @param endpoint The endpoint of the subscription to update
     * @param updatedAt The new updated timestamp
     */
    @Modifying
    @Transactional
    @Query("UPDATE PushSubscription s SET s.updatedAt = :updatedAt WHERE s.endpoint = :endpoint")
    void updateTimestamp(
            @Param("endpoint") String endpoint,
            @Param("updatedAt") LocalDateTime updatedAt
    );


}
