package com.vigiaenchente.collector.client;

import com.vigiaenchente.collector.dto.NewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class NewsApiClient {

    private final RestTemplate restTemplate;

    @Value("${newsapi.api.key}")
    private String apiKey;

    @Value("${newsapi.api.base-url}")
    private String baseUrl;

    public NewsResponse searchNews(String query, String language) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/everything")
                .queryParam("q", query)
                .queryParam("language", language)
                .queryParam("apiKey", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, NewsResponse.class);
    }
}

