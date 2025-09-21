package com.example.dailynewsapp.service;

import com.example.dailynewsapp.model.NewsResponse;
import com.example.dailynewsapp.model.NewsArticle;
import com.example.dailynewsapp.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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
                .timeout(Duration.ofSeconds(15))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> !(throwable instanceof java.net.SocketException)))
                .doOnError(error -> logger.error("Error fetching top headlines: {}", error.getMessage()))
                .onErrorReturn(createFallbackResponse("All News"));
    }

    public Mono<NewsResponse> getTopHeadlinesByCategory(String category) {
        String url = apiUrl + "?apiKey=" + apiKey + "&country=us&category=" + category + "&pageSize=20";
        logger.info("Fetching {} news from: {}", category, url);
        
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(NewsResponse.class)
                .timeout(Duration.ofSeconds(15))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> !(throwable instanceof java.net.SocketException)))
                .doOnError(error -> logger.error("Error fetching {} news: {}", category, error.getMessage()))
                .onErrorReturn(createFallbackResponse(category));
    }
    
    private NewsResponse createFallbackResponse(String category) {
        logger.info("Using fallback data for category: {}", category);
        
        NewsResponse response = new NewsResponse();
        response.setStatus("ok");
        response.setTotalResults(5);
        response.setMessage("Using sample data - API connection unavailable");
        response.setArticles(createSampleArticles(category));
        
        return response;
    }
    
    private List<NewsArticle> createSampleArticles(String category) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
        switch (category.toLowerCase()) {
            case "business":
                return Arrays.asList(
                    createSampleArticle("Tech Stocks Rally as Market Shows Strong Recovery", 
                        "Major technology companies see significant gains in today's trading session...", 
                        "https://example.com/business1", "Financial Times"),
                    createSampleArticle("Federal Reserve Announces Interest Rate Decision", 
                        "The central bank maintains current rates while monitoring inflation trends...", 
                        "https://example.com/business2", "Wall Street Journal"),
                    createSampleArticle("Global Supply Chain Shows Signs of Improvement", 
                        "Manufacturing and logistics sectors report better efficiency metrics...", 
                        "https://example.com/business3", "Reuters"),
                    createSampleArticle("Cryptocurrency Market Experiences Volatility", 
                        "Digital assets show mixed performance amid regulatory developments...", 
                        "https://example.com/business4", "CoinDesk"),
                    createSampleArticle("Energy Sector Leads Market Gains", 
                        "Oil and gas companies benefit from improved demand forecasts...", 
                        "https://example.com/business5", "Energy Weekly")
                );
                
            case "technology":
                return Arrays.asList(
                    createSampleArticle("AI Breakthrough in Medical Diagnosis", 
                        "New artificial intelligence system achieves 95% accuracy in detecting diseases...", 
                        "https://example.com/tech1", "TechCrunch"),
                    createSampleArticle("Quantum Computing Milestone Reached", 
                        "Researchers successfully maintain quantum state for record duration...", 
                        "https://example.com/tech2", "Nature"),
                    createSampleArticle("5G Network Expansion Accelerates", 
                        "Telecommunications companies roll out next-generation wireless infrastructure...", 
                        "https://example.com/tech3", "Wired"),
                    createSampleArticle("Cybersecurity Threats on the Rise", 
                        "Organizations face increasing challenges from sophisticated cyber attacks...", 
                        "https://example.com/tech4", "Security Weekly"),
                    createSampleArticle("Space Technology Advances", 
                        "Private companies make significant progress in space exploration...", 
                        "https://example.com/tech5", "Space News")
                );
                
            case "sports":
                return Arrays.asList(
                    createSampleArticle("Championship Game Ends in Dramatic Victory", 
                        "Underdog team secures unexpected win in final moments of the game...", 
                        "https://example.com/sports1", "ESPN"),
                    createSampleArticle("Olympic Preparations Enter Final Phase", 
                        "Athletes and organizers make last-minute adjustments for upcoming games...", 
                        "https://example.com/sports2", "Olympic News"),
                    createSampleArticle("Record-Breaking Performance in Track and Field", 
                        "Athlete sets new world record in spectacular fashion...", 
                        "https://example.com/sports3", "Track & Field Weekly"),
                    createSampleArticle("Professional League Announces Rule Changes", 
                        "Governing body introduces modifications to improve game safety...", 
                        "https://example.com/sports4", "Sports Authority"),
                    createSampleArticle("Youth Sports Programs Expand Nationwide", 
                        "Community initiatives promote physical activity among children...", 
                        "https://example.com/sports5", "Youth Sports Today")
                );
                
            case "health":
                return Arrays.asList(
                    createSampleArticle("Breakthrough in Cancer Treatment Research", 
                        "New therapy shows promising results in clinical trials...", 
                        "https://example.com/health1", "Medical Journal"),
                    createSampleArticle("Mental Health Awareness Campaign Launched", 
                        "Organizations promote resources for mental wellness support...", 
                        "https://example.com/health2", "Health Today"),
                    createSampleArticle("Nutrition Guidelines Updated", 
                        "Health authorities revise dietary recommendations based on new research...", 
                        "https://example.com/health3", "Nutrition Weekly"),
                    createSampleArticle("Telemedicine Services Expand", 
                        "Remote healthcare options become more accessible to patients...", 
                        "https://example.com/health4", "Digital Health"),
                    createSampleArticle("Exercise Science Research Findings", 
                        "Studies reveal optimal workout strategies for different age groups...", 
                        "https://example.com/health5", "Fitness Research")
                );
                
            default: // All News
                return Arrays.asList(
                    createSampleArticle("Global Climate Summit Reaches Historic Agreement", 
                        "World leaders commit to ambitious environmental protection measures...", 
                        "https://example.com/news1", "Global News"),
                    createSampleArticle("Scientific Discovery Opens New Research Frontiers", 
                        "Breakthrough findings could revolutionize multiple industries...", 
                        "https://example.com/news2", "Science Daily"),
                    createSampleArticle("Economic Indicators Show Positive Trends", 
                        "Key metrics suggest continued growth in major economies...", 
                        "https://example.com/news3", "Economic Times"),
                    createSampleArticle("Cultural Festival Celebrates Diversity", 
                        "Community event highlights traditions from around the world...", 
                        "https://example.com/news4", "Culture Weekly"),
                    createSampleArticle("Education Technology Transforms Learning", 
                        "Digital tools enhance student engagement and outcomes...", 
                        "https://example.com/news5", "Education Today")
                );
        }
    }
    
    private NewsArticle createSampleArticle(String title, String description, String url, String sourceName) {
        NewsArticle article = new NewsArticle();
        article.setTitle(title);
        article.setDescription(description);
        article.setUrl(url);
        article.setUrlToImage("https://via.placeholder.com/400x200?text=News+Image");
        article.setPublishedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        
        Source source = new Source();
        source.setName(sourceName);
        article.setSource(source);
        
        return article;
    }
}
