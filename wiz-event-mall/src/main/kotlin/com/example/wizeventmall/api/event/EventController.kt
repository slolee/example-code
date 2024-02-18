package com.example.wizeventmall.api.event

import com.example.wizeventmall.api.event.dto.EventInfoResponse
import com.example.wizeventmall.api.event.dto.EventParticipateResponse
import com.example.wizeventmall.api.event.dto.EventRegisterRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/events")
class EventController(
    private val eventService: EventService
) {

    @PostMapping
    fun registerEvent(@RequestBody req: EventRegisterRequest): ResponseEntity<Long> {
        return eventService.register(req)
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @GetMapping("/{eventId}")
    fun retrieveEventInfo(@PathVariable eventId: Long): ResponseEntity<EventInfoResponse> {
        return eventService.retrieve(eventId)
            .let { ResponseEntity.ok(it) }
    }

    // TODO : 구현 편의성을 위해 memberId 를 파라미터로 받음
    @PostMapping("/{eventId}/participate")
    fun participateEvent(
        @PathVariable eventId: Long,
        @RequestParam memberId: Long,
    ): ResponseEntity<EventParticipateResponse> {
        return eventService.participate(eventId, memberId)
            .let { ResponseEntity.ok(it) }
    }

}