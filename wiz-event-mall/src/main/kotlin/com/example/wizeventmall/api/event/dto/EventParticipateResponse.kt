package com.example.wizeventmall.api.event.dto

import com.example.wizeventmall.domain.event.entity.EventWinner

data class EventParticipateResponse(
    val isWinner: Boolean
) {

    companion object {
        fun from(result: EventWinner?) = EventParticipateResponse(
            isWinner = result != null
        )
    }
}