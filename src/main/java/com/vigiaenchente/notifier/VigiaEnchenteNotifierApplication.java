package com.vigiaenchente.notifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {
        "com.vigiaenchente.notifier",
        "com.vigiaenchente.core"
})
@EntityScan(basePackages = "com.vigiaenchente.core.domain.entity")
@EnableJpaRepositories(basePackages = "com.vigiaenchente.notifier.repository")
public class VigiaEnchenteNotifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(VigiaEnchenteNotifierApplication.class, args);
    }
}
