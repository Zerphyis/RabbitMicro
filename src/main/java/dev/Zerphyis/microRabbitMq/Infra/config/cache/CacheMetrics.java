package dev.Zerphyis.microRabbitMq.Infra.config.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class CacheMetrics {

    private final CacheManager cacheManager;
    private final AtomicLong hits = new AtomicLong(0);
    private final AtomicLong misses = new AtomicLong(0);

    public CacheMetrics(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Object get(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }

        Cache.ValueWrapper wrapper = cache.get(key);
        if (wrapper != null) {
            hits.incrementAndGet();
            return wrapper.get();
        } else {
            misses.incrementAndGet();
            return null;
        }
    }

    public void put(String cacheName, Object key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    public long getHits() {
        return hits.get();
    }

    public long getMisses() {
        return misses.get();
    }

    public double getHitRate() {
        long total = hits.get() + misses.get();
        return total == 0 ? 0.0 : (double) hits.get() / total * 100;
    }

    public void reset() {
        hits.set(0);
        misses.set(0);
    }
}
