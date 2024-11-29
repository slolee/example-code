package com.example.wizeventmalljava.api.member.dto;

public record MemberLoginRequest(
	String email,
	String password
) {
}
