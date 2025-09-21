package com.example.dailynewsapp.service;

import com.example.dailynewsapp.model.NewsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);
    private final WebClient webClient;
    
    @Value("${news.api.key}")
    private String apiKey;
    
    @Value("${news.api.url}")
    private String apiUrl;

    public NewsService() {
        this.webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .build();
    }

    public Mono<NewsResponse> getTopHeadlines() {
        String url = apiUrl + "?apiKey=" + apiKey + "&country=us&pageSize=20";
        logger.info("Fetching top headlines from: {}", url);
        
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(NewsResponse.class)
                .timeout(Duration.ofSeconds(30))
                .doOnError(error -> logger.error("Error fetching top headlines: {}", error.getMessage()))
                .onErrorReturn(createEmptyResponse("Failed to fetch top headlines"));
    }

    public Mono<NewsResponse> getTopHeadlinesByCategory(String category) {
        String url = apiUrl + "?apiKey=" + apiKey + "&country=us&category=" + category + "&pageSize=20";
        logger.info("Fetching {} news from: {}", category, url);
        
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(NewsResponse.class)
                .timeout(Duration.ofSeconds(30))
                .doOnError(error -> logger.error("Error fetching {} news: {}", category, error.getMessage()))
                .onErrorReturn(createEmptyResponse("Failed to fetch " + category + " news"));
    }
    
    private NewsResponse createEmptyResponse(String message) {
        NewsResponse response = new NewsResponse();
        response.setStatus("error");
        response.setTotalResults(0);
        response.setMessage(message);
        return response;
    }
}
