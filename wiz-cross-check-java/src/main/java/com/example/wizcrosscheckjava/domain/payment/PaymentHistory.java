package com.example.wizcrosscheckjava.domain.payment;

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
public class PaymentHistory {

	@Id @GeneratedValue
	@Column(name = "payment_history_id")
	private Long id;

	private String member;
	private String product;
	private Long amount;
	private final LocalDateTime createdAt = LocalDateTime.now();

	@Builder
	public PaymentHistory(Long id, String member, String product, Long amount) {
		this.id = id;
		this.member = member;
		this.product = product;
		this.amount = amount;
	}
}
