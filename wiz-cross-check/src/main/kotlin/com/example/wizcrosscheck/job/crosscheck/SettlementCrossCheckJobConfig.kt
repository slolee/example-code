package com.example.wizcrosscheck.job.crosscheck

import com.example.wizcrosscheck.domain.payment.PaymentHistory
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDate

@Configuration
class SettlementCrossCheckJobConfig(
    private val transactionManager: PlatformTransactionManager
) {

    // TODO : Job, Step 에 대한 Bean 들을 등록해준다!!
    // Spring Batch x.x (5.xx)

    @Bean
    fun settlementCrossCheckJob(
        jobRepository: JobRepository,
        createSettlementCrossCheckBaseStep: Step,
        appendPaymentAmountStep: Step,
        validateSettlementCrossCheckStep: Step
    ): Job {
        return JobBuilder("settlementCrossCheckJob", jobRepository)
            .start(createSettlementCrossCheckBaseStep)
            .next(appendPaymentAmountStep)
            .next(validateSettlementCrossCheckStep)
            .build()
    }

    // Step1. 정산내역을 토대로 대사 기반데이터 생성
    @Bean
    fun createSettlementCrossCheckBaseStep(
        jobRepository: JobRepository,
        createSettlementCrossCheckBaseTasklet: Tasklet
    ): Step {
        return StepBuilder("createSettlementCrossCheckBaseStep", jobRepository)
            .tasklet(createSettlementCrossCheckBaseTasklet, transactionManager)
            .build()
    }

    // Step2. 결제내역을 조회해 대사 테이블에 SUM 한 값을 누적
    // Reader - (Proccessor) - Writer
    @Bean
    fun appendPaymentAmountStep(
        @Value("\${cross-check-date}") date: LocalDate,
        jobRepository: JobRepository,
        entityManagerFactory: EntityManagerFactory,
        appendPaymentAmountWriter: ItemWriter<PaymentHistory>
    ): Step {
        return StepBuilder("appendPaymentAmountStep", jobRepository)
            .chunk<PaymentHistory, PaymentHistory>(1000, transactionManager)  // Reader, Writer
            .reader(
                // ItemReader 라는 인터페이스의 구현체
                JpaPagingItemReaderBuilder<PaymentHistory>()
                    .name("paymentHistoryItemReader")
                    .entityManagerFactory(entityManagerFactory)
                    .pageSize(1000)
                    .queryString("SELECT ph FROM PaymentHistory ph WHERE ph.createdAt BETWEEN :startDate AND :endDate") // JPQL
                    .parameterValues(
                        mapOf(
                            "startDate" to date.atStartOfDay(),
                            "endDate" to date.plusDays(1).atStartOfDay()
                        )
                    )
                    .build()
            )
            .writer(appendPaymentAmountWriter)
            .build()
    }

    @Bean
    fun validateSettlementCrossCheckStep(
        jobRepository: JobRepository,
        validateSettlementCrossCheckTasklet: Tasklet
    ): Step {
        return StepBuilder("validateSettlementCrossCheckStep", jobRepository)
            .tasklet(validateSettlementCrossCheckTasklet, transactionManager)
            .build()
    }
}