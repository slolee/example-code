package com.example.wizcrosscheckjava.domain.crosscheck;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementCrossCheckRepository extends JpaRepository<SettlementCrossCheck, Long> {

	Optional<SettlementCrossCheck> findByTargetDate(LocalDate date);
}
