package com.example.batchsample.domain.crosscheck

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SettlementCrossCheckRepository : JpaRepository<SettlementCrossCheck, Long> {

    fun findByTargetDate(date: LocalDate): SettlementCrossCheck?
}