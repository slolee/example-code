package com.example.wizshopjava.api.member;

import org.springframework.stereotype.Service;

import com.example.wizshopjava.api.member.dto.MemberLoginRequest;
import com.example.wizshopjava.api.member.dto.MemberRegisterRequest;
import com.example.wizshopjava.common.helper.JwtHelper;
import com.example.wizshopjava.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JwtHelper jwtHelper;

	public void register(MemberRegisterRequest req) {
		if (memberRepository.existsByEmail(req.email())) {
			throw new RuntimeException("이미 가입되어있는 이메일입니다!");
		}
		memberRepository.save(req.toEntity());
	}

	public String login(MemberLoginRequest req) {
		return memberRepository.findByEmail(req.email())
			.filter(member -> member.getPassword().equals(req.password()))
			.map(jwtHelper::generateAccessToken)
			.orElseThrow(() -> new RuntimeException("잘못된 아이디 혹은 패스워드입니다."));
	}
}
