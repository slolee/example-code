package com.example.wizcrosscheckjava.domain.settlement;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PgSettlementHistory {

	@Id @GeneratedValue
	@Column(name = "pg_settlement_history_id")
	private Long id;

	private String pgName;
	private Long amount;
	private LocalDate settlementDate;
	private final LocalDateTime createdAt = LocalDateTime.now();

	@Builder
	public PgSettlementHistory(Long id, String pgName, Long amount, LocalDate settlementDate) {
		this.id = id;
		this.pgName = pgName;
		this.amount = amount;
		this.settlementDate = settlementDate;
	}
}
