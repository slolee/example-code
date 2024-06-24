package com.example.wizcrosscheck.domain.settlement

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "pg_settlement_history")
class PgSettlementHistory(

    val pgName: String = "테스트용 임의의 PG사",
    val amount: Long,
    val settlementDate: LocalDate,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pg_settlement_history_id")
    var id: Long? = null
}