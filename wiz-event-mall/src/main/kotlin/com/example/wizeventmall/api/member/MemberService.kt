package com.example.wizeventmall.api.member

import com.example.wizeventmall.api.member.dto.MemberLoginRequest
import com.example.wizeventmall.api.member.dto.MemberRegisterRequest
import com.example.wizeventmall.common.helper.JwtHelper
import com.example.wizeventmall.domain.member.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtHelper: JwtHelper
) {

    @Transactional
    fun register(req: MemberRegisterRequest) {
        check(!memberRepository.existsByEmail(req.email)) { "이미 가입되어있는 이메일입니다." }

        val member = req.toEntity()
        member.encodePassword(passwordEncoder)
        memberRepository.save(member)
    }

    fun login(req: MemberLoginRequest): String {
        return memberRepository.findByEmail(req.email)
            ?.takeIf { passwordEncoder.matches(req.password, it.password) }
            ?.let { jwtHelper.generateAccessToken(it.id!!) }
            ?: throw RuntimeException("잘못된 아이디 혹은 패스워드입니다.")
    }
}