package com.example.wizcrosscheck.job.crosscheck

import com.example.wizcrosscheck.domain.crosscheck.SettlementCrossCheck
import com.example.wizcrosscheck.domain.crosscheck.SettlementCrossCheckRepository
import com.example.wizcrosscheck.domain.settlement.PgSettlementHistoryRepository
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CreateSettlementCrossCheckBaseTasklet(
    @Value("\${cross-check-date}") private val date: LocalDate,
    private val pgSettlementHistoryRepository: PgSettlementHistoryRepository,
    private val settlementCrossCheckRepository: SettlementCrossCheckRepository
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        // Business Logic
        //  1. 정산내역에서 특정 날짜를 조회한다.
        pgSettlementHistoryRepository.findBySettlementDate(date)
            //  2. 그 정산내역을 토대로 대사 데이터를 생성한다.
            ?.let { SettlementCrossCheck.createBase(it) }
            ?.let { settlementCrossCheckRepository.save(it) }
            ?: throw RuntimeException("정산 데이터 없음.. 돈 떼먹힌거")
        return RepeatStatus.FINISHED
    }
}