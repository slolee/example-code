package com.example.wizcrosscheckjava.batch.job;

import java.time.LocalDate;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.wizcrosscheckjava.domain.payment.PaymentHistory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SettlementCrossCheckJobConfig {

	private final PlatformTransactionManager transactionManager;

	@Bean
	public Job settlementCrossCheckJob(
		JobRepository jobRepository,
		Step createSettlementCrossCheckBaseStep,
		Step appendPaymentAmountStep,
		Step validateSettlementCrossCheckStep
	) {
		return new JobBuilder("settlementCrossCheckJob", jobRepository)
			.start(createSettlementCrossCheckBaseStep)
			.next(appendPaymentAmountStep)
			.next(validateSettlementCrossCheckStep)
			.build();
	}

	@Bean // Step1. 정산내역을 토대로 대사 기반데이터 생성
	public Step createSettlementCrossCheckBaseStep(
		JobRepository jobRepository,
		Tasklet createSettlementCrossCheckBaseTasklet
	) {
		return new StepBuilder("createSettlementCrossCheckBaseStep", jobRepository)
			.tasklet(createSettlementCrossCheckBaseTasklet, transactionManager)
			.build();
	}

	@Bean // Step2. 결제내역을 조회해 대사 테이블에 SUM 한 값을 누적
	public Step appendPaymentAmountStep(
		JobRepository jobRepository,
		ItemReader<PaymentHistory> appendPaymentAmountReader,
		ItemWriter<PaymentHistory> appendPaymentAmountWriter
	) {
		return new StepBuilder("appendPaymentAmountStep", jobRepository)
			.<PaymentHistory, PaymentHistory>chunk(1000, transactionManager)
			.reader(appendPaymentAmountReader)
			.writer(appendPaymentAmountWriter)
			.build();
	}

	@Bean // Step3. 정산내역 vs 결제내역 대사
	public Step validateSettlementCrossCheckStep(
		JobRepository jobRepository,
		Tasklet validateSettlementCrossCheckTasklet
	) {
		return new StepBuilder("validateSettlementCrossCheckStep", jobRepository)
			.tasklet(validateSettlementCrossCheckTasklet, transactionManager)
			.build();
	}
}
