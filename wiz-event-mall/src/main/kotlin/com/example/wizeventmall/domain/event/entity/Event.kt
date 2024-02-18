package com.example.wizeventmall.domain.event.entity

import com.example.wizeventmall.common.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "event")
class Event(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    var id: Long? = null,

    val name: String,
    val startAt: LocalDateTime,
    val winnerCount: Long,

    @Enumerated(EnumType.STRING)
    val status: EventStatus = EventStatus.IN_PROGRESS

): BaseEntity() {

    fun win(memberId: Long) = EventWinner(
        event = this,
        memberId = memberId
    )
}

enum class EventStatus {
    IN_PROGRESS, END, COUPON_ISSUE_COMPLETE
}