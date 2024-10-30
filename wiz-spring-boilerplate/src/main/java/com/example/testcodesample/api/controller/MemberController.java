package com.example.testcodesample.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testcodesample.api.controller.dto.AuthMember;
import com.example.testcodesample.api.controller.dto.MemberResponse;
import com.example.testcodesample.api.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/me")
	public ResponseEntity<MemberResponse> me(AuthMember authMember) {
		MemberResponse resp = memberService.retrieve(authMember.memberId());
		return ResponseEntity.ok(resp);
	}
}
