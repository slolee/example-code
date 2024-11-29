package com.example.wizeventmalljava.domain.event.entity;

import java.time.LocalDateTime;

import com.example.wizeventmalljava.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long id;

	private String name;
	private LocalDateTime startAt;
	private Long winnerCount;

	@Enumerated(EnumType.STRING)
	private EventStatus status = EventStatus.IN_PROGRESS;

	@Builder
	public Event(Long id, String name, LocalDateTime startAt, Long winnerCount) {
		this.id = id;
		this.name = name;
		this.startAt = startAt;
		this.winnerCount = winnerCount;
	}

	public boolean possibleParticipate(long currentWinnerCount) {
		return status == EventStatus.IN_PROGRESS
			&& startAt.isBefore(LocalDateTime.now())
			&& winnerCount > currentWinnerCount;
	}

	public EventWinner win(Long memberId) {
		return EventWinner.builder()
			.event(this)
			.memberId(memberId)
			.build();
	}
}
