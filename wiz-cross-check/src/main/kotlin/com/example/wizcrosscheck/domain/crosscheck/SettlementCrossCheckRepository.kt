package com.example.wizcrosscheck.domain.crosscheck

import com.example.wizcrosscheck.domain.crosscheck.SettlementCrossCheck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SettlementCrossCheckRepository : JpaRepository<SettlementCrossCheck, Long> {

    fun findByTargetDate(date: LocalDate): SettlementCrossCheck?
}