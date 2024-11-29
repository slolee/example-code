package com.example.wizeventmalljava.api.event;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.wizeventmalljava.domain.event.entity.Event;
import com.example.wizeventmalljava.domain.event.repository.EventRepository;
import com.example.wizeventmalljava.domain.event.repository.EventWinnerRepository;
import com.example.wizeventmalljava.domain.member.entity.Member;
import com.example.wizeventmalljava.domain.member.repository.MemberRepository;

@SpringBootTest
@ActiveProfiles("test")
public class EventServiceTest {

	private final static int THREAD_COUNT = 500;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EventWinnerRepository eventWinnerRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private EventService eventService;

	@Test
	@DisplayName(value = "Participate Event - 동시성 테스트")
	public void concurrencyTest() throws InterruptedException {
		// GIVEN
		var executor = Executors.newFixedThreadPool(THREAD_COUNT);
		var barrier = new CyclicBarrier(THREAD_COUNT);

		for (int i = 0; i < THREAD_COUNT; i++) {
			memberRepository.save(
				Member.builder().email("slolee@naver.com").password("TEST!@#$").nickname("wiz" + i).build());
		}
		Event event = eventRepository.save(Event.builder().
			name("Test Event")
			.startAt(LocalDateTime.now().minusHours(1))
			.winnerCount(100L)
			.build());

		// WHEN
		for (int i = 0; i < THREAD_COUNT; i++) {
			int memberId = i + 1;
			executor.execute(() -> {
				try {
					barrier.await(); // 모든 스레드가 동시에 시작되도록 대기
					eventService.participateWithLock(event.getId(), (long) memberId);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);

		// THEN
		assertThat(eventWinnerRepository.findAll().size()).isEqualTo(100);
	}
}
