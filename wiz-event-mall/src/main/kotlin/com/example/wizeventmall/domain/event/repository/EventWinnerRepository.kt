package com.example.wizeventmall.domain.event.repository

import com.example.wizeventmall.domain.event.entity.Event
import com.example.wizeventmall.domain.event.entity.EventWinner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventWinnerRepository: JpaRepository<EventWinner, Long> {
    fun existsByEventIdAndMemberId(eventId: Long, memberId: Long): Boolean
    fun countByEvent(event: Event): Long
}