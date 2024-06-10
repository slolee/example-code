package com.example.wiztodo.domain.member.repository

import com.example.wiztodo.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Member?
}