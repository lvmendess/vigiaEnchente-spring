package com.vigiaenchente.collector.service;

import com.vigiaenchente.collector.client.NewsApiClient;
import com.vigiaenchente.collector.dto.NewsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCollectorService {

    private final NewsApiClient newsApiClient;

    @Cacheable(value = "news", key = "#query")
    public List<NewsResponse.Article> getFloodNews(String query) {
        try {
            log.info("Fetching news for query: {}", query);
            NewsResponse response = newsApiClient.searchNews(query, "pt");

            if (response != null && response.getArticles() != null) {
                return response.getArticles().stream()
                        .limit(5)
                        .toList();
            }

            return List.of();
        } catch (Exception e) {
            log.error("Error fetching news", e);
            return List.of();
        }
    }

    public List<NewsResponse.Article> getFloodNews() {
        return getFloodNews("risco de enchente");
    }
}
