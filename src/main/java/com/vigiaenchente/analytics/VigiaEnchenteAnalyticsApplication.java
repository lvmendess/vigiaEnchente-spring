package com.vigiaenchente.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {
        "com.vigiaenchente.analytics",
        "com.vigiaenchente.collector",
        "com.vigiaenchente.core"
})
public class VigiaEnchenteAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VigiaEnchenteAnalyticsApplication.class, args);
    }
}

