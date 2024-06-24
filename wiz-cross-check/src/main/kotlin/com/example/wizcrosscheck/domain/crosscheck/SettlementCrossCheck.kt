package com.example.batchsample.domain.crosscheck

import com.example.batchsample.domain.settlement.PgSettlementHistory
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "settlement_cross_check")
class SettlementCrossCheck(

    val pgSettlementAmount: Long = 0,
    var totalPaymentAmount: Long = 0,
    var status: CrossCheckStatus = CrossCheckStatus.READY,
    val targetDate: LocalDate,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun appendPaymentAmount(totalPaymentAmount: Long): SettlementCrossCheck {
        this.totalPaymentAmount += totalPaymentAmount
        return this
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_cross_check_id")
    var id: Long? = null

    companion object {
        fun createBase(pgSettlementHistory: PgSettlementHistory) = SettlementCrossCheck(
            pgSettlementAmount = pgSettlementHistory.amount,
            targetDate = pgSettlementHistory.settlementDate
        )
    }
}

enum class CrossCheckStatus {
    READY, COMPLETE, MISMATCH
}