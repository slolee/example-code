package com.example.wizcrosscheckjava.batch.job.crosscheck;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.wizcrosscheckjava.domain.payment.PaymentHistory;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentHistoryReader {

	@Value("${cross-check-date}")
	private LocalDate date;

	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public JpaPagingItemReader<PaymentHistory> paymentHistoryJpaPagingItemReader() {
		return new JpaPagingItemReaderBuilder<PaymentHistory>()
			.name("paymentHistoryItemReader")
			.entityManagerFactory(entityManagerFactory)
			.pageSize(1000)
			.queryString("SELECT ph FROM PaymentHistory ph WHERE ph.createdAt BETWEEN :startDate AND :endDate") // JPQL
			.parameterValues(
				Map.of(
					"startDate", date.atStartOfDay(),
					"endDate", date.plusDays(1).atStartOfDay()
				)
			)
			.build();
	}
}
