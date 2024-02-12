package com.example.wizshop.api.member.dto

import com.example.wizshop.domain.member.entity.Member

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