package com.vigiaenchente.collector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsResponse {
    private String status;

    @JsonProperty("totalResults")
    private Integer totalResults;

    private List<Article> articles;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Article {
        private Source source;
        private String author;
        private String title;
        private String description;
        private String url;

        @JsonProperty("urlToImage")
        private String urlToImage;

        @JsonProperty("publishedAt")
        private String publishedAt;

        private String content;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Source {
            private String id;
            private String name;
        }
    }
}
