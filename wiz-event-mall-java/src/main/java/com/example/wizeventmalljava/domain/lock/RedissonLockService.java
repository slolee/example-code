package com.example.wizeventmalljava.domain.lock;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedissonLockService {

	private final RedissonClient redissonClient;

	public <T> T lock(String key, Duration timeout, Supplier<T> func) {
		RLock lock = redissonClient.getLock("LOCK:" + key);
		try {
			if (!lock.tryLock(timeout.getSeconds(), timeout.getSeconds(), TimeUnit.SECONDS)) {
				throw new RuntimeException("Lock 획득 실패!!");
			}
			return func.get();
		} catch (Exception ex) {
			throw new RuntimeException("Lock 획득 실패!!");
		} finally {
			lock.unlock();
		}
	}
}
