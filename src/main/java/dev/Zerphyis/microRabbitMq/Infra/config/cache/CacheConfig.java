package dev.Zerphyis.microRabbitMq.Infra.config.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@EnableCaching
public class CacheConfig {

    private final CacheMetrics cacheMetrics;

    @Autowired
    public CacheConfig(@Lazy CacheMetrics cacheMetrics) {
        this.cacheMetrics = cacheMetrics;
    }


    @Bean
    public CacheMetrics cacheMetrics() {
        return cacheMetrics;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
