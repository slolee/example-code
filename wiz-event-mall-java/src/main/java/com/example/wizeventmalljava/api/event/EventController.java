package com.example.wizeventmalljava.api.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wizeventmalljava.api.event.dto.EventCreateRequest;
import com.example.wizeventmalljava.api.event.dto.EventInfoResponse;
import com.example.wizeventmalljava.api.event.dto.EventParticipateResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

	private final EventService eventService;

	@PostMapping
	public ResponseEntity<Long> createEvent(@RequestBody EventCreateRequest req) {
		Long eventId = eventService.create(req);
		return ResponseEntity.ok(eventId);
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<EventInfoResponse> retrieveEventInfo(@PathVariable Long eventId) {
		EventInfoResponse response = eventService.retrieve(eventId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{eventId}/participate")
	public ResponseEntity<EventParticipateResponse> participateEvent(
		@PathVariable Long eventId,
		@RequestParam Long memberId
	) {
		EventParticipateResponse response = eventService.participate(eventId, memberId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{eventId}/participate/v2")
	public ResponseEntity<EventParticipateResponse> participateEventWithLock(
		@PathVariable Long eventId,
		@RequestParam Long memberId
	) {
		EventParticipateResponse response = eventService.participateWithLock(eventId, memberId);
		return ResponseEntity.ok(response);
	}
}
