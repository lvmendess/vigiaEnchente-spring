package com.vigiaenchente.collector.client;

import com.vigiaenchente.collector.dto.IpInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class IpInfoClient {

    private final RestTemplate restTemplate;

    @Value("${ipinfo.api.token}")
    private String apiToken;

    @Value("${ipinfo.api.base-url}")
    private String baseUrl;

    public IpInfoResponse getIpInfo() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/json")
                .queryParam("token", apiToken)
                .toUriString();

        return restTemplate.getForObject(url, IpInfoResponse.class);
    }
}
