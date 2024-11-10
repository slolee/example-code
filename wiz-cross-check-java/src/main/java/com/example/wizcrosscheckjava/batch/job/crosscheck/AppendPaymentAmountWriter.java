package com.example.wizcrosscheckjava.batch.job.crosscheck;

import java.time.LocalDate;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.wizcrosscheckjava.domain.crosscheck.SettlementCrossCheckRepository;
import com.example.wizcrosscheckjava.domain.payment.PaymentHistory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppendPaymentAmountWriter implements ItemWriter<PaymentHistory> {

	@Value("${cross-check-date}")
	private LocalDate date;

	private final SettlementCrossCheckRepository settlementCrossCheckRepository;

	@Override
	public void write(Chunk<? extends PaymentHistory> chunk) throws Exception {
		// 1. 특정 날짜에 대한 정산대사 레코드 조회
		settlementCrossCheckRepository.findByTargetDate(date)
			.map(settlementCrossCheck -> {
				// 2. 해당 레코드에 TotalPaymentAmount 에 지금 조회된 결제내역 총합더해서
				long totalAmount = chunk.getItems().stream().mapToLong(PaymentHistory::getAmount).sum();
				// 3. 누적
				settlementCrossCheck.appendPaymentAmount(totalAmount);
				return settlementCrossCheckRepository.save(settlementCrossCheck);
			})
			.orElseThrow();
	}
}
