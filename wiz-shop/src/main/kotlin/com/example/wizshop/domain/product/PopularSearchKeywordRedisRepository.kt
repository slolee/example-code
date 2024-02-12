package com.example.wizshop.domain.product

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PopularSearchKeywordRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    private val zSetOps = redisTemplate.opsForZSet()

    fun increment(keyword: String) {
        zSetOps.incrementScore(KEY, "KEYWORD_$keyword", 1.0)
    }

    fun findTopN(n: Long = 5): List<String> {
        return zSetOps.reverseRange(KEY, 0, n - 1)
            ?.toList()
            ?: emptyList()
    }

    companion object {
        private const val KEY = "POPULAR_SEARCH_KEYWORD"
    }
}