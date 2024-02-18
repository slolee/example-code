package com.example.wizeventmall.domain.event.entity

import com.example.wizeventmall.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "event_winner")
class EventWinner(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_winner_id")
    var id: Long? = null,

    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val event: Event,

    val memberId: Long

): BaseEntity() {
}