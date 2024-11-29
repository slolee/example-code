package com.example.wizeventmalljava.domain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wizeventmalljava.domain.event.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
