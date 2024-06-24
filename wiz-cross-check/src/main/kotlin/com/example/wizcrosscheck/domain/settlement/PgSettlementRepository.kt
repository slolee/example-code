package com.example.batchsample.domain.settlement

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface PgSettlementHistoryRepository : JpaRepository<PgSettlementHistory, Long> {
    fun findBySettlementDate(date: LocalDate): PgSettlementHistory?
}