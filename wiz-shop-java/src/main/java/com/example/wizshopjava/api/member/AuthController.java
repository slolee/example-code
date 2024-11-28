package com.example.wizshopjava.api.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wizshopjava.api.member.dto.MemberLoginRequest;
import com.example.wizshopjava.api.member.dto.MemberRegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final MemberService memberService;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody MemberRegisterRequest req) {
		memberService.register(req);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody MemberLoginRequest req) {
		String accessToken = memberService.login(req);
		return ResponseEntity.ok(accessToken);
	}
}
