package com.example.wizeventmall.api.event.dto

import com.example.wizeventmall.domain.event.entity.Event
import java.time.LocalDateTime

data class EventRegisterRequest(
    val eventName: String,
    val startAt: LocalDateTime,
    val winnerCount: Long
) {

    fun toEntity() = Event(
        name = eventName,
        startAt = startAt,
        winnerCount = winnerCount
    )
}