package com.example.wizshopjava.domain.product.repository;

import static java.util.Collections.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
public class PopularSearchKeywordRedisRepository {

	private static final String KEY = "POPULAR_SEARCH_KEYWORD";
	private static final String KEYWORD_PREFIX = "KEYWORD_";

	private final RedisTemplate<String, String> redisTemplate;
	private final ZSetOperations<String, String> zSetOps;

	public PopularSearchKeywordRedisRepository(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.zSetOps = redisTemplate.opsForZSet();
	}

	public void increment(String keyword) {
		zSetOps.incrementScore(KEY, KEYWORD_PREFIX + keyword, 1.0);
	}

	public List<String> findTopN(long n) {
		return Optional.of(zSetOps.reverseRange(KEY, 0, n - 1))
			.orElse(emptySet())
			.stream()
			.map(keyword -> keyword.substring(KEYWORD_PREFIX.length()))
			.toList();
	}
}
