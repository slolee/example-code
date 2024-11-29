package com.example.wizeventmalljava.api.event.dto;

import java.time.LocalDateTime;

import com.example.wizeventmalljava.domain.event.entity.Event;
import com.example.wizeventmalljava.domain.event.entity.EventStatus;

public record EventInfoResponse(
	Long eventId,
	String eventName,
	LocalDateTime startAt,
	Long winnerCount,
	EventStatus status
) {
	public static EventInfoResponse from(Event event) {
		return new EventInfoResponse(
			event.getId(),
			event.getName(),
			event.getStartAt(),
			event.getWinnerCount(),
			event.getStatus()
		);
	}
}
