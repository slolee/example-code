package com.example.wizcrosscheck.domain.crosscheck

import com.example.wizcrosscheck.domain.settlement.PgSettlementHistory
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "settlement_cross_check")
class SettlementCrossCheck(

    val pgSettlementAmount: Long = 0,  // 정산 금액
    var totalPaymentAmount: Long = 0,  // 총 결제 금액
    var status: CrossCheckStatus = CrossCheckStatus.READY,  // 대사 상태 -> 대기, 성공, 불일치
    val targetDate: LocalDate,  // 대사 날짜
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