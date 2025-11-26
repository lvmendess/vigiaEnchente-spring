package com.vigiaenchente.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@ComponentScan(basePackages = {
        "com.vigiaenchente.api",
        "com.vigiaenchente.collector",
        "com.vigiaenchente.analytics",
        "com.vigiaenchente.notifier"
})
@EntityScan(basePackages = "com.vigiaenchente.core.domain.entity")
/*@EnableJpaRepositories(basePackages = {
        "com.vigiaenchente.api.repository",
       // "com.vigiaenchente.notifier.repository"
})

 */
public class VigiaEnchenteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VigiaEnchenteApiApplication.class, args);
    }
}


