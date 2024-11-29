package com.example.wizeventmalljava.api.member.dto;

import com.example.wizeventmalljava.domain.member.entity.Member;

public record MemberRegisterRequest(
	String email,
	String password,
	String nickname
) {

	public Member toEntity() {
		return Member.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.build();
	}
}
