package com.example.wizshop.domain.config.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheManagerConfig {

    @Bean
    fun caffeineCacheList(): List<CaffeineCache> {
        return CacheType.entries.map { type ->
            Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(type.timeToLive, TimeUnit.SECONDS)
                .build<Any, Any>()
                .let { CaffeineCache(type.name, it) }
        }
    }

    @Bean
    fun cacheManager(caffeineCacheList: List<CaffeineCache>): CacheManager {
        return SimpleCacheManager()
            .apply { this.setCaches(caffeineCacheList) }
    }
}