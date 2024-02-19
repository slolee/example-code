package com.example.wizeventmall.api.member.dto

data class MemberLoginRequest(
    val email: String,
    val password: String
) {
}