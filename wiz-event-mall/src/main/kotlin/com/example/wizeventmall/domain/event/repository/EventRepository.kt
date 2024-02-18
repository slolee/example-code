package com.example.wizeventmall.domain.event.repository

import com.example.wizeventmall.domain.event.entity.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository: JpaRepository<Event, Long> {
}