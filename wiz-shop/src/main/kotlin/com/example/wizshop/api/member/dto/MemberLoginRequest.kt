package com.example.wizshop.api.member.dto

data class MemberLoginRequest(
    val email: String,
    val password: String
) {
}