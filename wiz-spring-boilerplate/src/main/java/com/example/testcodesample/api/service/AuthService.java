package com.example.testcodesample.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.testcodesample.api.controller.dto.LoginRequest;
import com.example.testcodesample.api.controller.dto.LoginResponse;
import com.example.testcodesample.api.controller.dto.RegisterRequest;
import com.example.testcodesample.api.exception.BusinessException;
import com.example.testcodesample.api.exception.DuplicateEmailException;
import com.example.testcodesample.api.exception.ErrorCode;
import com.example.testcodesample.common.JwtHelper;
import com.example.testcodesample.domain.member.Member;
import com.example.testcodesample.domain.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtHelper jwtHelper;

	public void register(RegisterRequest req) {
		if (memberRepository.existsByEmail(req.email())) {
			throw new DuplicateEmailException();
		}
		var member = Member.builder()
			.email(req.email())
			.password(passwordEncoder.encode(req.password()))
			.nickname(req.nickname())
			.build();
		memberRepository.save(member);
	}

	public LoginResponse login(LoginRequest req) {
		Member member = memberRepository.findByEmail(req.email())
			.orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_FAILED));
		if (!passwordEncoder.matches(req.password(), member.getPassword())) {
			throw new BusinessException(ErrorCode.LOGIN_FAILED);
		}
		String accessToken = jwtHelper.generateAccessToken(member);
		return new LoginResponse(accessToken);
	}
}
