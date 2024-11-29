package com.example.wizeventmalljava.api.event.dto;

import java.time.LocalDateTime;

import com.example.wizeventmalljava.domain.event.entity.Event;

public record EventCreateRequest(
	String eventName,
	LocalDateTime startAt,
	Long winnerCount
) {
	public Event toEntity() {
		return Event.builder()
			.name(eventName)
			.startAt(startAt)
			.winnerCount(winnerCount)
			.build();
	}
}
