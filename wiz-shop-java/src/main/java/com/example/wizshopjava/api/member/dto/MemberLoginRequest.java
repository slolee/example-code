package com.example.wizshopjava.api.member.dto;

public record MemberLoginRequest(
	String email,
	String password
) {
}
