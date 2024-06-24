package com.example.wizcrosscheck.domain.payment

import com.example.wizcrosscheck.domain.payment.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentHistoryRepository : JpaRepository<PaymentHistory, Long> {
}