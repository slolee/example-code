package com.example.wizshop.domain.config

import com.example.wizshop.domain.product.entity.Product
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Page
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@EnableCaching
@Configuration
class CacheManagerConfig {

    @Bean
    fun cacheManager(
        objectMapper: ObjectMapper,
        redisConnectionFactory: RedisConnectionFactory
    ): CacheManager {
        val config = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(JdkSerializationRedisSerializer()))
        return RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
            .withCacheConfiguration("PRODUCT_SEARCH", config)
            .build()
    }
}