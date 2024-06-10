package com.example.wiztodo.api.service

import com.example.wiztodo.api.controller.dto.request.MemberRegisterRequest
import com.example.wiztodo.common.JwtHelper
import com.example.wiztodo.domain.member.entity.Member
import com.example.wiztodo.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtHelper: JwtHelper
) {

    fun register(req: MemberRegisterRequest) {
        /**
         * 2. 위 정보중 이메일로 가입된 사용자가 이미 존재하는지 확인한다.
         * 3. 위 정보중 패스워드를 암호화한다.
         * 4. 사용자 정보를 생성해 데이터베이스에 저장한다.
         */
        if (memberRepository.existsByEmail(req.email)) {
            throw RuntimeException()
        }
        // ...
        val member = Member(email = req.email, password = req.password)
        memberRepository.save(member)
    }

    fun login(req: MemberRegisterRequest): String {
        val member = memberRepository.findByEmail(req.email)
            ?: throw RuntimeException()
        if (member.validPassword(req.password)) {
            throw RuntimeException()
        }
        return jwtHelper.generateAccessToken(member.id!!)
    }
}