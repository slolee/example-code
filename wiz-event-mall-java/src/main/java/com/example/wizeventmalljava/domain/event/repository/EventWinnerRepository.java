package com.example.wizeventmalljava.domain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wizeventmalljava.domain.event.entity.Event;
import com.example.wizeventmalljava.domain.event.entity.EventWinner;

@Repository
public interface EventWinnerRepository extends JpaRepository<EventWinner, Long> {

	boolean existsByEventIdAndMemberId(Long eventId, Long memberId);
	Long countByEvent(Event event);
}
