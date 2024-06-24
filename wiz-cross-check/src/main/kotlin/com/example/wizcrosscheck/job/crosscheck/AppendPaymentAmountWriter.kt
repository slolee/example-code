package com.example.wizcrosscheck.job.crosscheck

import com.example.wizcrosscheck.domain.crosscheck.SettlementCrossCheckRepository
import com.example.wizcrosscheck.domain.payment.PaymentHistory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class AppendPaymentAmountWriter(
    @Value("\${cross-check-date}") private val date: LocalDate,
    private val settlementCrossCheckRepository: SettlementCrossCheckRepository
) : ItemWriter<PaymentHistory> {

    // Chunk 최대 크기는 몇개가 올수 있을까요? ->
    override fun write(chunk: Chunk<out PaymentHistory>) {
        // Business Logic
        //  1. 특정 날짜에 대한 정산대사 레코드 조회
        settlementCrossCheckRepository.findByTargetDate(date)
            //  2. 해당 레코드에 TotalPaymentAmount 에 지금 조회된 결제내역 총합더해서 누적
            ?.let { it.appendPaymentAmount(chunk.sumOf { i -> i.amount }) }
            ?.let { settlementCrossCheckRepository.save(it) }
    }
}