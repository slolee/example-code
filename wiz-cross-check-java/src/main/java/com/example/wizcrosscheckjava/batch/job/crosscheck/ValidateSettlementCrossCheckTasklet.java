package com.example.wizcrosscheckjava.batch.job.crosscheck;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.wizcrosscheckjava.domain.crosscheck.CrossCheckStatus;
import com.example.wizcrosscheckjava.domain.crosscheck.SettlementCrossCheck;
import com.example.wizcrosscheckjava.domain.crosscheck.SettlementCrossCheckRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateSettlementCrossCheckTasklet implements Tasklet {

	@Value("${cross-check-date}")
	private LocalDate date;

	private final SettlementCrossCheckRepository settlementCrossCheckRepository;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// 1. 특정 날짜에 대한 정산대사 정보 조회
		SettlementCrossCheck settlementCrossCheck = settlementCrossCheckRepository.findByTargetDate(date).orElseThrow();

		// 2. 정산내역과 총 결제금액이 동일한지 비교
		settlementCrossCheck.validate();

		// 3. 불일치 발생시 로그남기고 저장
		if (settlementCrossCheck.getStatus() == CrossCheckStatus.MISMATCH) {
			log.error("%s 일대사 불일치 - 제휴사 정산금액: %s원 / 총 결제금액: %s원".formatted(
				settlementCrossCheck.getTargetDate(),
				settlementCrossCheck.getPgSettlementAmount(),
				settlementCrossCheck.getTotalPaymentAmount())
			);
		}
		settlementCrossCheckRepository.save(settlementCrossCheck);
		return RepeatStatus.FINISHED;
	}
}
