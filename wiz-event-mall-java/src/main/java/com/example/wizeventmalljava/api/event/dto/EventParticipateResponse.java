package com.example.wizeventmalljava.api.event.dto;

import com.example.wizeventmalljava.domain.event.entity.EventWinner;

public record EventParticipateResponse(
	boolean isWinner
) {
	public static EventParticipateResponse from(EventWinner eventWinner) {
		return new EventParticipateResponse(eventWinner != null);
	}
}
