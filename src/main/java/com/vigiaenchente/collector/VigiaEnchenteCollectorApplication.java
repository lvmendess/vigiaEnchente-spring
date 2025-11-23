package com.vigiaenchente.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {
        "com.vigiaenchente.collector",
        "com.vigiaenchente.core"
})
public class VigiaEnchenteCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(VigiaEnchenteCollectorApplication.class, args);
    }
}
