package com.example.wizcrosscheck.domain.payment

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment_history")
class PaymentHistory(

    val member: String = "테스트용 임의 사용자",
    val productName: String,
    val amount: Long,
    val createdAt: LocalDateTime
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_history_id")
    var id: Long? = null
}