package com.example.wizeventmalljava.domain.event.entity;

import com.example.wizeventmalljava.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventWinner extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_winner_id")
	private Long id;

	@JoinColumn(name = "event_id", referencedColumnName = "event_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Event event;

	private Long memberId;

	@Builder
	public EventWinner(Long id, Event event, Long memberId) {
		this.id = id;
		this.event = event;
		this.memberId = memberId;
	}
}
