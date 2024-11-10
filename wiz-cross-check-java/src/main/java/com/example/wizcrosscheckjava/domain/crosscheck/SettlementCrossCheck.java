package com.example.wizcrosscheckjava.domain.crosscheck;

import java.time.LocalDate;
import java.util.Objects;

import com.example.wizcrosscheckjava.domain.settlement.PgSettlementHistory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SettlementCrossCheck {

	@Id
	@GeneratedValue
	@Column(name = "settlement_cross_check_id")
	private Long id;

	private Long pgSettlementAmount;
	private Long totalPaymentAmount;

	@Enumerated(EnumType.STRING)
	private CrossCheckStatus status;
	private LocalDate targetDate;

	@Builder
	public SettlementCrossCheck(
		Long id,
		Long pgSettlementAmount,
		Long totalPaymentAmount,
		CrossCheckStatus status,
		LocalDate targetDate
	) {
		this.id = id;
		this.pgSettlementAmount = pgSettlementAmount;
		this.totalPaymentAmount = totalPaymentAmount;
		this.status = status;
		this.targetDate = targetDate;
	}

	public static SettlementCrossCheck createBase(PgSettlementHistory settlementHistory) {
		return SettlementCrossCheck.builder()
			.pgSettlementAmount(settlementHistory.getAmount())
			.totalPaymentAmount(0L)
			.status(CrossCheckStatus.READY)
			.targetDate(settlementHistory.getSettlementDate())
			.build();
	}

	public void appendPaymentAmount(long totalAmount) {
		this.totalPaymentAmount += totalAmount;
	}

	public void validate() {
		if (Objects.equals(pgSettlementAmount, totalPaymentAmount)) {
			status = CrossCheckStatus.COMPLETE;
		} else {
			status = CrossCheckStatus.MISMATCH;
		}
	}
}
