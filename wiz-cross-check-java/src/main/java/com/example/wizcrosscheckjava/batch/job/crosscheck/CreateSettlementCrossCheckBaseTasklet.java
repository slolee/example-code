package com.example.wizcrosscheckjava.batch.job.crosscheck;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.wizcrosscheckjava.domain.crosscheck.SettlementCrossCheck;
import com.example.wizcrosscheckjava.domain.crosscheck.SettlementCrossCheckRepository;
import com.example.wizcrosscheckjava.domain.settlement.PgSettlementHistoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateSettlementCrossCheckBaseTasklet implements Tasklet {

	@Value("${cross-check-date}")
	private LocalDate date;

	private final PgSettlementHistoryRepository pgSettlementHistoryRepository;
	private final SettlementCrossCheckRepository settlementCrossCheckRepository;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		// 1. 특정 날짜의 정산내역을 조회한다.
		pgSettlementHistoryRepository.findBySettlementDate(date)
			.map(settlementHistory -> {
				// 2. 정산내역을 토대로 대사 기반데이터를 생성한다.
				SettlementCrossCheck base = SettlementCrossCheck.createBase(settlementHistory);
				return settlementCrossCheckRepository.save(base);
			})
			.orElseThrow();
		return RepeatStatus.FINISHED;
	}
}
