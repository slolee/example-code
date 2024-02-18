package com.example.wizeventmall.api.event.dto

import com.example.wizeventmall.domain.event.entity.Event
import com.example.wizeventmall.domain.event.entity.EventStatus
import java.time.LocalDateTime

data class EventInfoResponse(
    val eventId: Long,
    val eventName: String,
    val startAt: LocalDateTime,
    val winnerCount: Long,
    val status: EventStatus
) {
    companion object {
        fun from(event: Event) = EventInfoResponse(
            eventId = event.id!!,
            eventName = event.name,
            startAt = event.startAt,
            winnerCount = event.winnerCount,
            status = event.status
        )
    }
}