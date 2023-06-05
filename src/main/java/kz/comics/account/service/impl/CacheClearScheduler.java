package kz.comics.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheClearScheduler {

    private final CacheManager cacheManager;

    @Autowired
    public CacheClearScheduler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 3600000) // Execute every hour (adjust the value as needed)
    public void clearCache() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}