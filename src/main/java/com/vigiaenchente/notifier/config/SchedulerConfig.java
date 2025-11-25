package com.vigiaenchente.notifier.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Enables scheduled tasks for periodic notifications
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {
    // Enables Spring's scheduled task execution capability
}
