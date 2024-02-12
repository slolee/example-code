package com.example.wizshop.domain.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PopularSearchKeywordRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    private val zSetOps = redisTemplate.opsForZSet()

    fun increment(keyword: String) {
        zSetOps.incrementScore(KEY, "$KEYWORD_PREFIX$keyword", 1.0)
    }

    fun findTopN(n: Long = 5): List<String> {
        return zSetOps.reverseRange(KEY, 0, n - 1)
            ?.map { it.substring(KEYWORD_PREFIX.length) }
            ?.toList()
            ?: emptyList()
    }

    companion object {
        private const val KEY = "POPULAR_SEARCH_KEYWORD"
        private const val KEYWORD_PREFIX = "KEYWORD_"
    }
}