package com.example.wizcrosscheckjava.domain.settlement;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PgSettlementHistoryRepository extends JpaRepository<PgSettlementHistory, Long> {
	Optional<PgSettlementHistory> findBySettlementDate(LocalDate date);
}
