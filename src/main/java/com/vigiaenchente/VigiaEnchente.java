package com.vigiaenchente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EntityScan(basePackages = "com.vigiaenchente.core.domain.entity")
@EnableJpaRepositories(basePackages = {
        "com.vigiaenchente.api.repository",
        //"com.vigiaenchente.notifier.repository"
})
public class VigiaEnchente {

    public static void main(String[] args) {
        SpringApplication.run(VigiaEnchente.class, args);
    }
}
