package com.example.wizcrosscheck.job.crosscheck

import com.example.wizcrosscheck.domain.crosscheck.CrossCheckStatus
import com.example.wizcrosscheck.domain.crosscheck.SettlementCrossCheckRepository
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ValidateSettlementCrossCheckTasklet(
    @Value("\${cross-check-date}") private val date: LocalDate,
    private val settlementCrossCheckRepository: SettlementCrossCheckRepository
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        // Business Logic
        //  1. 특정 날짜에 대한 정산대사 정보 조회
        settlementCrossCheckRepository.findByTargetDate(date)
            //  2. 정산내역과 총 결제금액이 동일한지 비교
            ?.also {
                if (it.pgSettlementAmount == it.totalPaymentAmount) {
                    it.status = CrossCheckStatus.COMPLETE
                } else {
                    it.status = CrossCheckStatus.MISMATCH
                    throw RuntimeException("$date 일대사 불일치 - 제휴사: ${it.pgSettlementAmount}원 / 결제내역: ${it.totalPaymentAmount}원")
                }
            }
            ?.let { settlementCrossCheckRepository.save(it) }
        return RepeatStatus.FINISHED
    }
}