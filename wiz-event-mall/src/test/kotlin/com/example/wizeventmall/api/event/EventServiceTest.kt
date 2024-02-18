package com.example.wizeventmall.api.event

import com.example.wizeventmall.domain.event.entity.Event
import com.example.wizeventmall.domain.event.repository.EventRepository
import com.example.wizeventmall.domain.event.repository.EventWinnerRepository
import com.example.wizeventmall.domain.member.entity.Member
import com.example.wizeventmall.domain.member.repository.MemberRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SpringBootTest
@ActiveProfiles("test")
class EventServiceTest @Autowired constructor(
    private val eventService: EventService,

    private val eventRepository: EventRepository,
    private val eventWinnerRepository: EventWinnerRepository,
    private val memberRepository: MemberRepository
) {

    @Test
    fun `Participate Event - 동시성 테스트`() {
        // GIVEN
        val executor = Executors.newFixedThreadPool(THREAD_COUNT)
        val barrier = CyclicBarrier(THREAD_COUNT)

        // 1. Thread 수만큼 사용자 생성
        repeat(THREAD_COUNT) {
            memberRepository.save(Member(email = "slolee@naver.com", password = "TEST!@#$", nickname = "wiz$it"))
        }
        // 2. Event 정보 생성
        val event = Event(
            name = "Test Event",
            startAt = LocalDateTime.now().minusHours(1),
            winnerCount = 10
        ).let { eventRepository.save(it) }

        // WHEN
        repeat(THREAD_COUNT) {
            executor.execute {
                barrier.await()
                eventService.participate(eventId = event.id!!, memberId = it.toLong() + 1)
            }
        }
        executor.awaitTermination(5, TimeUnit.SECONDS)

        // THEN
        eventWinnerRepository.findAll().size shouldBe 10
    }

    companion object {
        private const val THREAD_COUNT = 100
    }
}