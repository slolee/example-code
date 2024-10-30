package com.example.testcodesample.api.controller.dto;

import com.example.testcodesample.domain.member.Member;

public record MemberResponse(String email, String nickname) {
	public static MemberResponse from(Member member) {
		return new MemberResponse(member.getEmail(), member.getNickname());
	}
}
