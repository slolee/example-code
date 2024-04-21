package com.example.wizeventmall.api.event

import com.example.wizeventmall.api.event.dto.EventInfoResponse
import com.example.wizeventmall.api.event.dto.EventParticipateResponse
import com.example.wizeventmall.api.event.dto.EventRegisterRequest
import com.example.wizeventmall.domain.event.repository.EventRepository
import com.example.wizeventmall.domain.event.repository.EventWinnerRepository
import com.example.wizeventmall.domain.lock.RedissonLock
import com.example.wizeventmall.domain.member.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val eventWinnerRepository: EventWinnerRepository,
    private val memberRepository: MemberRepository
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

//    @Transactional
//    fun participate(eventId: Long, memberId: Long): EventParticipateResponse {
//        check(memberRepository.existsById(memberId)) { "잘못된 사용자 정보입니다." }
//        check(!eventWinnerRepository.existsByEventIdAndMemberId(eventId, memberId)) { "이미 이벤트에 참여하셨습니다." }
//
//        return eventRepository.findByIdOrNull(eventId)!!.takeIf {
//            val winnerCount = eventWinnerRepository.countByEvent(it)
//            it.possibleParticipate(currentWinnerCount = winnerCount)
//        }?.let {
//            eventWinnerRepository.save(it.win(memberId))
//        }.let {
//            EventParticipateResponse.from(it)
//        }
//    }

    @Transactional
    fun participate(eventId: Long, memberId: Long): EventParticipateResponse = RedissonLock("PARTICIPATE:$eventId") {
        check(memberRepository.existsById(memberId)) { "잘못된 사용자 정보입니다." }
        check(!eventWinnerRepository.existsByEventIdAndMemberId(eventId, memberId)) { "이미 이벤트에 참여하셨습니다." }

        eventRepository.findByIdOrNull(eventId)!!.takeIf {
            val winnerCount = eventWinnerRepository.countByEvent(it)
            it.possibleParticipate(currentWinnerCount = winnerCount)
        }?.let {
            eventWinnerRepository.save(it.win(memberId))
        }.let {
            EventParticipateResponse.from(it)
        }
    }
}