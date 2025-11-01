package com.vigiaenchente.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VigiaEnchenteAPI_Application {
    public static void main(String[] args) {
        SpringApplication.run(VigiaEnchenteAPI_Application.class, args);
    }
}
