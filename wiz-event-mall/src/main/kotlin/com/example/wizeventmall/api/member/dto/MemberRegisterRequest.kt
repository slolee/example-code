package com.example.wizeventmall.api.member.dto

import com.example.wizeventmall.domain.member.entity.Member

data class MemberRegisterRequest(
    val email: String,
    val password: String,
    val nickname: String
) {

    fun toEntity() = Member(
        email = this.email,
        password = this.password,
        nickname = this.nickname
    )
}