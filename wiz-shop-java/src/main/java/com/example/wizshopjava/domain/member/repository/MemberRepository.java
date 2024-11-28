package com.example.wizshopjava.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wizshopjava.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(String email);
	Optional<Member> findByEmail(String email);
}
