package com.example.wizeventmall.domain.lock

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.TimeUnit

@Component
class RedissonLock(
    tmpAdvice: LockAdvice
) {

    init {
        advice = tmpAdvice
    }
    @Component
    class LockAdvice(
        private val redissonClient: RedissonClient
    ) {

        operator fun <T> invoke(key: String, timeout: Duration, func: () -> T): T {
            val lock = redissonClient.getLock("LOCK:$key")
            if (!lock.tryLock(timeout.seconds, timeout.seconds, TimeUnit.SECONDS)) {
                throw RuntimeException("LOCK 획득 실패!")
            }
            return func().also { lock.unlock() }
        }
    }

    companion object {
        private lateinit var advice: LockAdvice

        operator fun <T> invoke(key: String, timeout: Duration = Duration.ofSeconds(60), func: () -> T): T {
            return when(this::advice.isInitialized) {
                true -> advice(key, timeout, func)
                false -> func()
            }
        }
    }
}