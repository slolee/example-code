package com.example.wizeventmalljava.api.event;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wizeventmalljava.api.event.dto.EventCreateRequest;
import com.example.wizeventmalljava.api.event.dto.EventInfoResponse;
import com.example.wizeventmalljava.api.event.dto.EventParticipateResponse;
import com.example.wizeventmalljava.domain.event.entity.Event;
import com.example.wizeventmalljava.domain.event.entity.EventWinner;
import com.example.wizeventmalljava.domain.event.repository.EventRepository;
import com.example.wizeventmalljava.domain.event.repository.EventWinnerRepository;
import com.example.wizeventmalljava.domain.lock.RedissonLockService;
import com.example.wizeventmalljava.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final EventWinnerRepository eventWinnerRepository;
	private final MemberRepository memberRepository;
	private final RedissonLockService redissonLockService;

	public Long create(EventCreateRequest req) {
		Event event = eventRepository.save(req.toEntity());
		return event.getId();
	}

	public EventInfoResponse retrieve(Long eventId) {
		return eventRepository.findById(eventId)
			.map(EventInfoResponse::from)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이벤트입니다. (" + eventId + ")"));
	}

	@Transactional
	public EventParticipateResponse participate(Long eventId, Long memberId) {
		if (!memberRepository.existsById(memberId)) {
			throw new IllegalArgumentException("잘못된 사용자 정보입니다.");
		}
		if (eventWinnerRepository.existsByEventIdAndMemberId(eventId, memberId)) {
			throw new IllegalStateException("이미 이벤트에 참여하셨습니다.");
		}

		Event event = eventRepository.findById(eventId)
			.orElseThrow(() -> new RuntimeException("이벤트를 찾을 수 없습니다. (" + eventId + ")"));
		long winnerCount = eventWinnerRepository.countByEvent(event);
		if (!event.possibleParticipate(winnerCount)) {
			throw new IllegalStateException("이벤트 참여가 불가능합니다.");
		}

		EventWinner eventWinner = eventWinnerRepository.save(event.win(memberId));
		return EventParticipateResponse.from(eventWinner);
	}

	@Transactional
	public EventParticipateResponse participateWithLock(Long eventId, Long memberId) {
		return redissonLockService.lock(
			"PARTICIPATE:" + eventId,
			Duration.ofSeconds(60),
			() -> participate(eventId, memberId));
	}
}
