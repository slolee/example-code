package com.example.wizeventmall.api.event

import com.example.wizeventmall.api.event.dto.EventInfoResponse
import com.example.wizeventmall.api.event.dto.EventParticipateResponse
import com.example.wizeventmall.api.event.dto.EventRegisterRequest
import com.example.wizeventmall.domain.event.repository.EventRepository
import com.example.wizeventmall.domain.event.repository.EventWinnerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val eventWinnerRepository: EventWinnerRepository
) {

    fun register(req: EventRegisterRequest): Long {
        return eventRepository.save(req.toEntity())
            .let { it.id!! }
    }

    fun retrieve(eventId: Long): EventInfoResponse {
        return eventRepository.findByIdOrNull(eventId)
            ?.let { EventInfoResponse.from(it) }
            ?: throw RuntimeException("존재하지 않는 이벤트입니다. ($eventId)")
    }

    fun participate(eventId: Long, memberId: Long): EventParticipateResponse {
        check(!eventWinnerRepository.existsByEventIdAndMemberId(eventId, memberId)) { "이미 이벤트에 참여하셨습니다." }

        return eventRepository.findByIdOrNull(eventId)!!
            .takeIf { eventWinnerRepository.countByEvent(it) < it.winnerCount }
            ?.let { eventWinnerRepository.save(it.win(memberId)) }
            .let { EventParticipateResponse.from(it) }
    }
}