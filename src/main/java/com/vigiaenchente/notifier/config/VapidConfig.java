package com.vigiaenchente.notifier.config;

import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.GeneralSecurityException;
import java.security.Security;

@Configuration
@Slf4j
public class VapidConfig {

    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    @Value("${vapid.subject}")
    private String subject;

    @Bean
    public PushService pushService() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());

        PushService pushService = new PushService();
        pushService.setPublicKey(publicKey);
        pushService.setPrivateKey(privateKey);
        pushService.setSubject(subject);

        log.info("PushService configured successfully");

        return pushService;
    }
}
