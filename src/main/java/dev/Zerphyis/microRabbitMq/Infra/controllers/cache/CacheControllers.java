package dev.Zerphyis.microRabbitMq.Infra.controllers.cache;


import dev.Zerphyis.microRabbitMq.Infra.config.cache.CacheMetrics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class CacheControllers {
    private final CacheMetrics metrics;

    public CacheControllers(CacheMetrics metrics) {
        this.metrics = metrics;
    }

    @GetMapping("/metrics/cache")
    public Map<String, Object> getCacheMetrics() {
        return Map.of(
                "hits", metrics.getHits(),
                "misses", metrics.getMisses(),
                "hit_rate_percent", String.format("%.2f%%", metrics.getHitRate())
        );
    }

    @PostMapping("/metrics/cache/reset")
    public Map<String, Object> resetMetrics() {
        metrics.reset();
        return Map.of(
                "message", "Requisições Resetadas",
                "hits", metrics.getHits(),
                "misses", metrics.getMisses(),
                "hit_rate_percent", metrics.getHitRate()
        );
    }
}
